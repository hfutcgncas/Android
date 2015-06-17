package com.example.gyroscopetest;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	private float Euler[] = new float[3];
	TextView acclere_disp_b[] = new TextView [3];
	
	private SensorManager sensorManager;
	private Sensor gyroscopeSensor;
	
	
	private static final float NS2S = 1.0f / 1000000000.0f;
	private float timestamp = 0;

	//----------------------------------------------------------//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
				
		acclere_disp_b[0] = (TextView)findViewById(R.id.X_accelerID_b);
		acclere_disp_b[1] = (TextView)findViewById(R.id.Y_accelerID_b);
		acclere_disp_b[2] = (TextView)findViewById(R.id.Z_accelerID_b);
		
		//-----------------------------------//
		sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		sensorManager.registerListener(new SensorEventListener(){

			@Override
			public void onAccuracyChanged(Sensor arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSensorChanged(SensorEvent event) {
			
	//			System.out.println("x____" + event.values[0]);
	//			System.out.println("y____" + event.values[1]);
	//			System.out.println("z____" + event.values[2]);
				if (timestamp != 0) {
				    final float dT = (event.timestamp - timestamp) * NS2S;
				    Euler[0] += event.values[0] * dT;
				    Euler[1] += event.values[1] * dT;
				    Euler[2] += event.values[2] * dT;
				}
				timestamp = event.timestamp;
				   	
				String strX = Euler[0] + "";
				String strY = Euler[1] + "";
				String strZ = Euler[2] + "";		
				
				acclere_disp_b[0].setText(strX);
				acclere_disp_b[1].setText(strY);
				acclere_disp_b[2].setText(strZ);
			}
			
		}, gyroscopeSensor, sensorManager.SENSOR_DELAY_NORMAL); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	


}
