package com.cardboardptz;


import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class Gyroscope {
	
	// angular speeds from gyro
    private float[] gyro = new float[3];
 
    // rotation matrix from gyro data
    private float[] gyroMatrix = new float[9];
 
    // orientation angles from gyro matrix
    private float[] gyroOrientation = new float[3];
 
    // magnetic field vector
    private float[] magnet = new float[3];
 
    // accelerometer vector
    private float[] accel = new float[3];
 
    // orientation angles from accel and magnet
    private float[] accMagOrientation = new float[3];
 
    // final orientation angles from sensor fusion
    private float[] fusedOrientation = new float[3];
 
    // accelerometer and magnetometer based rotation matrix
    private float[] rotationMatrix = new float[9];
    // accelerometer and magnetometer based SetZero matrix
    private float[] SetZeroOrientation = new float[3];
    
    
    private SensorManager mSensorManager = null;	
    
    public float[] OrientationDegree = new float[3];
	
	public static final int TIME_CONSTANT = 30;
	public static final float FILTER_COEFFICIENT = 0.98f;
	
	private Timer fuseTimer = null;
	
	private SocketChannel socketChannel = null;
	public boolean mRun = false;
//-----------------------------------------------------------------------	
	public SocketChannel SetsockerChannel(SocketChannel in)
	{
		socketChannel = in;
		return socketChannel;
	}
	public Gyroscope( FullscreenActivity  mainActivity)
	{
		gyroOrientation[0] = 0.0f;
        gyroOrientation[1] = 0.0f;
        gyroOrientation[2] = 0.0f;
        
        SetZeroOrientation[0] = 0.0f;
        SetZeroOrientation[1] = 0.0f;
        SetZeroOrientation[2] = 0.0f;
 
        // initialise gyroMatrix with identity matrix
        gyroMatrix[0] = 1.0f; gyroMatrix[1] = 0.0f; gyroMatrix[2] = 0.0f;
        gyroMatrix[3] = 0.0f; gyroMatrix[4] = 1.0f; gyroMatrix[5] = 0.0f;
        gyroMatrix[6] = 0.0f; gyroMatrix[7] = 0.0f; gyroMatrix[8] = 1.0f;
        
        // get sensorManager and initialise sensor listeners
        mSensorManager = (SensorManager) mainActivity.getSystemService("sensor");
        initListeners();
        

      //      socket = socketChannel.socket();  
            //向服务器发送消息  
       //     send2PTZ = new PrintWriter(  new OutputStreamWriter(socket.getOutputStream()),true);        
            
       //     send2PTZ.println("I'm here");
           
	}
	
	protected void finalize()
    {
		try {
			if(socketChannel.isOpen())
			socketChannel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		// add something....................
    }
	//-----------------------------------------------
	public void initListeners(){
		
	    mSensorManager.registerListener(new SensorEventListener(){

			@Override
			public void onAccuracyChanged(Sensor arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onSensorChanged(SensorEvent event) {
		        System.arraycopy(event.values, 0, accel, 0, 3);
		        calculateAccMagOrientation();
			}
			
		},mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
	        SensorManager.SENSOR_DELAY_FASTEST);
	 
	    mSensorManager.registerListener(new SensorEventListener(){

			@Override
			public void onAccuracyChanged(Sensor arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onSensorChanged(SensorEvent event) {
		        gyroFunction(event);			
			}
			
		},
	        mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
	        SensorManager.SENSOR_DELAY_FASTEST);
	 
	    mSensorManager.registerListener(new SensorEventListener(){

			@Override
			public void onAccuracyChanged(Sensor arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onSensorChanged(SensorEvent event) {
		        System.arraycopy(event.values, 0, magnet, 0, 3);
			}
			
		},
	        mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
	        SensorManager.SENSOR_DELAY_FASTEST);
	} 
	
	public void setOriZeroPos(float[] Src, float[] Cof)
	{
		//将Cof 设为Src的转置，即逆； 同时把Src设为单位阵
		Cof[1] = Src[3];
		Cof[2] = Src[6];
		Cof[5] = Src[7];
		Cof[3] = Src[1];
		Cof[6] = Src[2];
		Cof[7] = Src[5];
		
		Src[0] = 1.0f; Src[1] = 0.0f; Src[2] = 0.0f;
		Src[3] = 0.0f; Src[4] = 1.0f; Src[5] = 0.0f;
		Src[6] = 0.0f; Src[7] = 0.0f; Src[8] = 1.0f;	
	}
	public void calculateAccMagOrientation() {

	    //这里是否要加入方向零位设定的部分
	    if(   SensorManager.getRotationMatrix(rotationMatrix, null, accel, magnet) )
	    {
	    	
	    	//if( SetZeroMatrix == null ) //在起始时设置SetZeroMatrix)
	    	//{
	    	//	setOriZeroPos(rotationMatrix,SetZeroMatrix);
	    	//}
	    	//rotationMatrix = matrixMultiplication(SetZeroMatrix, rotationMatrix);
	    	/////////
	    	SensorManager.getOrientation(rotationMatrix, accMagOrientation);
	    	accMagOrientation[2] = accMagOrientation[2] - SetZeroOrientation[2];
	    }	    
	}
	
	
	public static final float EPSILON = 0.000000001f;
	 
	private void getRotationVectorFromGyro(float[] gyroValues,
	                                       float[] deltaRotationVector,
	                                       float timeFactor)
	{
	    float[] normValues = new float[3];
	 
	    // Calculate the angular speed of the sample
	    float omegaMagnitude =
	        (float)Math.sqrt(gyroValues[0] * gyroValues[0] +
	        gyroValues[1] * gyroValues[1] +
	        gyroValues[2] * gyroValues[2]);
	 
	    // Normalize the rotation vector if it's big enough to get the axis
	    if(omegaMagnitude > EPSILON) {
	        normValues[0] = gyroValues[0] / omegaMagnitude;
	        normValues[1] = gyroValues[1] / omegaMagnitude;
	        normValues[2] = gyroValues[2] / omegaMagnitude;
	    }
	 
	    // Integrate around this axis with the angular speed by the timestep
	    // in order to get a delta rotation from this sample over the timestep
	    // We will convert this axis-angle representation of the delta rotation
	    // into a quaternion before turning it into the rotation matrix.
	    float thetaOverTwo = omegaMagnitude * timeFactor;
	    float sinThetaOverTwo = (float)Math.sin(thetaOverTwo);
	    float cosThetaOverTwo = (float)Math.cos(thetaOverTwo);
	    deltaRotationVector[0] = sinThetaOverTwo * normValues[0];
	    deltaRotationVector[1] = sinThetaOverTwo * normValues[1];
	    deltaRotationVector[2] = sinThetaOverTwo * normValues[2];
	    deltaRotationVector[3] = cosThetaOverTwo;
	} 
	
	private static final float NS2S = 1.0f / 1000000000.0f;
	private float timestamp;
	private boolean initState = true;

	
	
	private float[] matrixMultiplication(float[] A, float[] B) {
	    float[] result = new float[9];
	 
	    result[0] = A[0] * B[0] + A[1] * B[3] + A[2] * B[6];
	    result[1] = A[0] * B[1] + A[1] * B[4] + A[2] * B[7];
	    result[2] = A[0] * B[2] + A[1] * B[5] + A[2] * B[8];
	 
	    result[3] = A[3] * B[0] + A[4] * B[3] + A[5] * B[6];
	    result[4] = A[3] * B[1] + A[4] * B[4] + A[5] * B[7];
	    result[5] = A[3] * B[2] + A[4] * B[5] + A[5] * B[8];
	 
	    result[6] = A[6] * B[0] + A[7] * B[3] + A[8] * B[6];
	    result[7] = A[6] * B[1] + A[7] * B[4] + A[8] * B[7];
	    result[8] = A[6] * B[2] + A[7] * B[5] + A[8] * B[8];
	 
	    return result;
	}
	
	private float[] getRotationMatrixFromOrientation(float[] o) {
	    float[] xM = new float[9];
	    float[] yM = new float[9];
	    float[] zM = new float[9];
	 
	    float sinX = (float)Math.sin(o[1]);
	    float cosX = (float)Math.cos(o[1]);
	    float sinY = (float)Math.sin(o[2]);
	    float cosY = (float)Math.cos(o[2]);
	    float sinZ = (float)Math.sin(o[0]);
	    float cosZ = (float)Math.cos(o[0]);
	 
	    // rotation about x-axis (pitch)
	    xM[0] = 1.0f; xM[1] = 0.0f; xM[2] = 0.0f;
	    xM[3] = 0.0f; xM[4] = cosX; xM[5] = sinX;
	    xM[6] = 0.0f; xM[7] = -sinX; xM[8] = cosX;
	 
	    // rotation about y-axis (roll)
	    yM[0] = cosY; yM[1] = 0.0f; yM[2] = sinY;
	    yM[3] = 0.0f; yM[4] = 1.0f; yM[5] = 0.0f;
	    yM[6] = -sinY; yM[7] = 0.0f; yM[8] = cosY;
	 
	    // rotation about z-axis (azimuth)
	    zM[0] = cosZ; zM[1] = sinZ; zM[2] = 0.0f;
	    zM[3] = -sinZ; zM[4] = cosZ; zM[5] = 0.0f;
	    zM[6] = 0.0f; zM[7] = 0.0f; zM[8] = 1.0f;
	 
	    // rotation order is y, x, z (roll, pitch, azimuth)
	    float[] resultMatrix = matrixMultiplication(xM, yM);
	    resultMatrix = matrixMultiplication(zM, resultMatrix);
	    return resultMatrix;
	}
	
	@SuppressLint("NewApi")
	public void gyroFunction(SensorEvent event) {
	    // don't start until first accelerometer/magnetometer orientation has been acquired
	    if (accMagOrientation == null)
	        return;
	 
	    // initialisation of the gyroscope based rotation matrix
	    if(initState) {
	        float[] initMatrix = new float[9];
	        initMatrix = getRotationMatrixFromOrientation(accMagOrientation);
	        float[] test = new float[3];
	        SensorManager.getOrientation(initMatrix, test);
	        /////
	        SetZeroOrientation[2] = accMagOrientation[2];
	        
	        gyroMatrix = matrixMultiplication(gyroMatrix, initMatrix);
	        initState = false;
	    }
	 
	    // copy the new gyro values into the gyro array
	    // convert the raw gyro data into a rotation vector
	    float[] deltaVector = new float[4];
	    if(timestamp != 0) {
	        final float dT = (event.timestamp - timestamp) * NS2S;
	    System.arraycopy(event.values, 0, gyro, 0, 3);
	    getRotationVectorFromGyro(gyro, deltaVector, dT / 2.0f);
	    }
	 
	    // measurement done, save current time for next interval
	    timestamp = event.timestamp;
	 
	    // convert rotation vector into rotation matrix
	    float[] deltaMatrix = new float[9];
	    SensorManager.getRotationMatrixFromVector(deltaMatrix, deltaVector);
	 
	    // apply the new rotation interval on the gyroscope based rotation matrix
	    gyroMatrix = matrixMultiplication(gyroMatrix, deltaMatrix);
	 
	    // get the gyroscope based orientation from the rotation matrix
	    SensorManager.getOrientation(gyroMatrix, gyroOrientation);
	}  
	
	class calculateFusedOrientationTask extends TimerTask {
	    public void run() {
	        float oneMinusCoeff = 1.0f - FILTER_COEFFICIENT;

	        for(int i = 0;i<3;i++)
	        {
	        	fusedOrientation[i] =
	    	            FILTER_COEFFICIENT * gyroOrientation[i]
	    	            + oneMinusCoeff * accMagOrientation[i];
	        	OrientationDegree[i] = fusedOrientation[i]*180;
	        	OrientationDegree[i] = OrientationDegree[i]/3.1415926f;
	        }
	         
	        // overwrite gyro matrix and orientation with fused orientation
	        // to comensate gyro drift
	        gyroMatrix = getRotationMatrixFromOrientation(fusedOrientation);
	        System.arraycopy(fusedOrientation, 0, gyroOrientation, 0, 3);
	        
	        
	    //    send2PTZ.println("BG:P"+(int)OrientationDegree[0]+"R"+(int)OrientationDegree[1] + "ED");
	        

	    //    System.out.println("x____" + OrientationDegree[0]);
		//	System.out.println("y____" + OrientationDegree[1]);
		//	System.out.println("z____" + OrientationDegree[2]);
	    }
	}
	
	//=========================================================
	public class GyroCommThread extends Thread {
		public void run() {
			while(mRun){
		        StringBuilder sbcmd = new StringBuilder();
		        sbcmd.append("X");
		        sbcmd.append(""+OrientationDegree[0]);
		        sbcmd.append("Y");
		        sbcmd.append(""+OrientationDegree[2]);
		        sbcmd.append("\r\n");
		        
		        System.out.println("again:"+new String(sbcmd));
		        byte[] sendBytes = new String(sbcmd).getBytes();
		        try {
					socketChannel.write(ByteBuffer.wrap(sendBytes));				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
		        try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private GyroCommThread thread;
	
	public void onStart(){
		if(fuseTimer == null)
		{
			fuseTimer = new Timer();
		}
		fuseTimer.scheduleAtFixedRate(new calculateFusedOrientationTask(),
                1000, TIME_CONSTANT);
		
		mRun = true;
		if(thread==null){
        	thread = new GyroCommThread();
        }
        thread.start();    	
	}
	public void onStop(){
		if(fuseTimer!=null)
		{
			fuseTimer.cancel();
		}
		fuseTimer = null; //
		mRun = false;

		if(thread!=null){
        	boolean retry = true;
	        while(retry) {
	            try {
	                thread.join();
	                retry = false;
	            } catch (InterruptedException e) {}
	        }
	        thread = null;
        }
	}

}
