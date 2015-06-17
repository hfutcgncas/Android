package com.Liu.toycarv2;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	TextView DispTextview;
	LocationManager lm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//==============================================================
		lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		DispTextview = (TextView)findViewById(R.id.DispTxt);
		DispTextview.setText("asdasd");
		
		//==============================================================
   
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
