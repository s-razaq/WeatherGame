package com.sample;

import com.sample.HowToUseWheelPickerActivity.NumberAdapter;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class GameActivity extends Activity {
	
	public void onCreate (Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.play_screen);
	        
	        final Intent intent = this.getIntent();

	        String difficulty = intent.getStringExtra("difficulty");
	        System.out.println(difficulty);
	        
	        TextView stadt = (TextView) findViewById(R.id.stadt);
	        Button enterButton =(Button) findViewById(R.id.enterButton);
	        
	        //Random City
	        RandCity randCity = RandCity.getInstance(this.getApplicationContext());
	        String city = randCity.getCity(difficulty);
	        stadt.setText(city);
	         
	        WheelView temValue = (WheelView) findViewById(R.id.value);
	        temValue.setViewAdapter(new NumberClass(this));
	        temValue.setCurrentItem(60);
	   }
}
