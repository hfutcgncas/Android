package com.liu.flutev2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	Button button;
	EditText editText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		button = (Button)findViewById(R.id.button1);
		button.setOnClickListener(new ButtonListener());
		
		editText = (EditText)findViewById(R.id.editText1);
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
			Bundle bundle = new Bundle();
			bundle.putString("text_view", editText.getText().toString());
			Intent intent=new Intent();  
	        intent.setClass(MainActivity.this, FigureActivity.class);  
	        
	        intent.putExtras(bundle);
	        startActivityForResult(intent, 0);

		}
		
	}

}
