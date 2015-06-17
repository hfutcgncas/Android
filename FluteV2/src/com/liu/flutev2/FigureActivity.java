package com.liu.flutev2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class FigureActivity extends Activity {
	TextView textview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_figure);
		
		Bundle bundle = getIntent().getExtras();
		textview = (TextView)findViewById(R.id.textView1);
		textview.setText(bundle.getString("text_view"));
		
		Toast.makeText(this, "×ª»»Íê³É", Toast.LENGTH_SHORT).show(); 
	}
}
