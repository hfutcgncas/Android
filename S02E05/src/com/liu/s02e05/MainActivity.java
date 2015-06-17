package com.liu.s02e05;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView textview;
	private Button button;
	private ProgressBar progressBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		textview = (TextView)findViewById(R.id.textViewId);
		button   = (Button)findViewById(R.id.buttonId);
		progressBar = (ProgressBar)findViewById(R.id.progressBar1);
		
		button.setOnClickListener(new ButtonListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	class ButtonListener implements OnClickListener{
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Thread t = new MyThread();
			t.start();
		//	progressBar.setProgress(progressBar.getProgress()+10);
		}
	}
	
	class MyThread extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
		//	super.run();
			for(int i = 0;i < 100 ; i++){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				progressBar.setProgress(progressBar.getProgress()+1);
			}
		}
	}

}
