package com.liu.flutetool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	private Button   CovertButton;  	
	private ButtonListener buttonListener;	
	public String InputString = new String();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		 
		CovertButton = (Button)findViewById(R.id.button1);
		CovertButton.setOnClickListener(buttonListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

	 
	 class ButtonListener implements OnClickListener{
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
	         Intent intent=new Intent();  
	         intent.setClass(MainActivity.this, FigureActivity.class);  
	         startActivity(intent); 
	         
		}


	 }
	 

}
