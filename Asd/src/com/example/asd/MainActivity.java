package com.example.asd;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends Activity {
	private TextView textView;
	private Button button;
	private ProgressBar progressBar;
	int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
    
     
	    textView = (TextView)findViewById(R.id.textView1);
	    textView.setText("0");
	    textView.setBackgroundColor(Color.CYAN);
	    
	    progressBar = (ProgressBar)findViewById(R.id.progressBar1);
	    button = (Button)findViewById(R.id.button1);
	    ButtonListener buttonListener = new ButtonListener();
	    button.setOnClickListener(buttonListener);
    
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
			count++;
			if(count>10){
				count = 0;
			}
			textView.setText(count + "");	
			progressBar.setProgress(count*20);
			progressBar.setSecondaryProgress(200-count*20);
		}
    }
    
}
