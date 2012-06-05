package com.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BestWeatherGameEverActivity extends Activity {
    /** Called when the activity is first created. */
	public static final String DIFF_EASY = "easy";
	public static final String DIFF_MEDIUM = "medium";
	public static final String DIFF_HARD = "hard";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);
        Gamification score = Gamification.getInstance();
        Button easyButton =(Button) findViewById(R.id.easyButton);
        easyButton.setClickable(true);
        Button middleButton =(Button) findViewById(R.id.middleButton);
        if (score.getScore()<= 100){ //100 variabel
        	middleButton.setClickable(false);
        	middleButton.getBackground().setAlpha(50);
        }
        Button hardButton =(Button) findViewById(R.id.hardButton);
        if (score.getScore()<= 350){ //350 variabel
        	hardButton.setClickable(false);
        	hardButton.getBackground().setAlpha(50);
        }
        TextView punktestandAnzeige = (TextView) findViewById(R.id.PunktestandAnzeige);
        int i = score.getScore();
        punktestandAnzeige.setText("" + i);
        TextView levelAnzeige = (TextView) findViewById(R.id.LevelAnzeige);
        levelAnzeige.setText(score.getLevelName());
        
        RandCity randCity = RandCity.getInstance(this.getApplicationContext());
        randCity.loadCSV();
       
    }
    
    
    public void onClickEasy(final View myView) {
    	final Intent intent = new Intent(this, GameActivity.class);
    	intent.putExtra("difficulty", DIFF_EASY);
    	startActivity(intent);
    }
}