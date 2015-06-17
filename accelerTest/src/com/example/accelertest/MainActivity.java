package com.example.accelertest;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	private SensorManager sensorManager;
	private Sensor accelerometerSensor;
	
	private float gravity[] = new float[3];
	private float linerAcceleration[] = new float[3];
	
	TextView acclere_disp_b[] = new TextView [3];
	
	TextView X_acclere_disp;
	TextView Y_acclere_disp;
	TextView Z_acclere_disp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		acclere_disp_b[0] = (TextView)findViewById(R.id.X_accelerID_b);
		acclere_disp_b[1] = (TextView)findViewById(R.id.Y_accelerID_b);
		acclere_disp_b[2] = (TextView)findViewById(R.id.Z_accelerID_b);
		
		X_acclere_disp = (TextView)findViewById(R.id.X_accelerID);
		Y_acclere_disp = (TextView)findViewById(R.id.Y_accelerID);
		Z_acclere_disp = (TextView)findViewById(R.id.Z_accelerID);
		
		sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(new SensorEventListener() {
			
			@Override
			public void onSensorChanged(SensorEvent event) {
				// TODO Auto-generated method stub
//				System.out.println("x____" + event.values[0]);
//				System.out.println("y____" + event.values[1]);
//				System.out.println("z____" + event.values[2]);
				
				final float alpha = 0.8f;
				gravity[0] = alpha*gravity[0] + (1-alpha)*event.values[0];
				gravity[1] = alpha*gravity[1] + (1-alpha)*event.values[1];
				gravity[2] = alpha*gravity[2] + (1-alpha)*event.values[2];
				
				linerAcceleration[0] = event.values[0] - gravity[0];
				linerAcceleration[1] = event.values[1] - gravity[1];
				linerAcceleration[2] = event.values[2] - gravity[2];
								
				System.out.println("x____" + linerAcceleration[0]);
				System.out.println("y____" + linerAcceleration[1]);
				System.out.println("z____" + linerAcceleration[2]);
				
				
				String strXb = event.values[0] + "";
				String strYb = event.values[1] + "";
				String strZb = event.values[2] + "";
				
				acclere_disp_b[0].setText(strXb);
				acclere_disp_b[1].setText(strYb);
				acclere_disp_b[2].setText(strZb);
				
				String strX = linerAcceleration[0] + "";
				String strY = linerAcceleration[1] + "";
				String strZ = linerAcceleration[2] + "";
				
				X_acclere_disp.setText(strX);
				Y_acclere_disp.setText(strY);
				Z_acclere_disp.setText(strZ);

			}
			
			@Override
			public void onAccuracyChanged(Sensor arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		}, accelerometerSensor, sensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
