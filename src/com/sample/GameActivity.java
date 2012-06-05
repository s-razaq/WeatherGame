package com.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends Activity {
	
	   public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        final Intent intent = this.getIntent();

	        String difficulty = intent.getStringExtra("difficulty");
	        System.out.println(difficulty);

	        
	        setContentView(R.layout.play_screen);
	        TextView stadt = (TextView) findViewById(R.id.stadt);
	        Button enterButton =(Button) findViewById(R.id.enterButton);
	   
	   
	        //Random City
	        RandCity randCity = RandCity.getInstance(this.getApplicationContext());
	        String city = randCity.getCity(difficulty);
	        stadt.setText(city);		        
	   
	   }
}
