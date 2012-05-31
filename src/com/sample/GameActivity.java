package com.sample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends Activity {
	
	   public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.play_screen);
	        TextView stadt = (TextView) findViewById(R.id.stadt);
	        Button enterButton =(Button) findViewById(R.id.enterButton);
	   }
}
