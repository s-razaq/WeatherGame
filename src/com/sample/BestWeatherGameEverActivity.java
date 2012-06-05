package com.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BestWeatherGameEverActivity extends Activity {
    /** Called when the activity is first created. */
	public static final String LEVEL_EASY = "easy";
	public static final String LEVEL_MEDIUM = "medium";
	public static final String LEVEL_HARD = "hard";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);
        Button easyButton =(Button) findViewById(R.id.easyButton);
        easyButton.setClickable(true);
        Button middleButton =(Button) findViewById(R.id.middleButton);
        middleButton.setClickable(false);
        Button hardButton =(Button) findViewById(R.id.hardButton);
        hardButton.setClickable(false);
        TextView punktestandAnzeige = (TextView) findViewById(R.id.PunktestandAnzeige);
        TextView levelAnzeige = (TextView) findViewById(R.id.LevelAnzeige);
    }
    
    
    public void onClickEasy(final View myView) {
    	final Intent intent = new Intent(this, GameActivity.class);
    	intent.putExtra(LEVEL_EASY, 1);
    	//intent.putExtra(RAND_CITY, reference to RandCity Singleton);
    	startActivity(intent);
    }
}