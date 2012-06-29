package com.sample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BestWeatherGameEverActivity extends Activity {
	/** Called when the activity is first created. */
	public static final String DIFF_EASY = "easy";
	public static final String DIFF_MEDIUM = "medium";
	public static final String DIFF_HARD = "hard";
	public static Gamification score;
	public static Button easyButton;
	public static Button middleButton;
	public static Button hardButton;
	public static TextView punktestandAnzeige;
	public static TextView levelAnzeige;
	 

	AlertDialog alertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);       
        
        checkConn();
        
        score = Gamification.getInstance(this.getApplicationContext());
        easyButton =(Button) findViewById(R.id.easyButton);
        easyButton.setClickable(true);
        middleButton =(Button) findViewById(R.id.middleButton);
        if (!score.isMediumLevelActivated()){
        	middleButton.setClickable(false);
        	middleButton.getBackground().setAlpha(50);
        }
        hardButton =(Button) findViewById(R.id.hardButton);
        if (!score.isExpertLevelActivated()){
        	hardButton.setClickable(false);
        	hardButton.getBackground().setAlpha(50);
        }
        punktestandAnzeige = (TextView) findViewById(R.id.PunktestandAnzeige);
        int i = score.getScore();
        punktestandAnzeige.setText("" + i);
        levelAnzeige = (TextView) findViewById(R.id.LevelAnzeige);
        levelAnzeige.setText(score.getLevelName());
        
        RandCity randCity = RandCity.getInstance(this.getApplicationContext());
        randCity.loadCSV();     
       
    }
    
    
    public void onClickEasy(final View myView) {
    	final Intent intent = new Intent(this, GameActivity.class);
    	intent.putExtra("difficulty", DIFF_EASY);
    	startActivity(intent);
    }
    
    public void onClickMedium(final View myView) {
    	final Intent intent = new Intent(this, GameActivity.class);
    	intent.putExtra("difficulty", DIFF_MEDIUM);
    	startActivity(intent);
    }
    
    public void onClickHard(final View myView) {
    	final Intent intent = new Intent(this, GameActivity.class);
    	intent.putExtra("difficulty", DIFF_HARD);
    	startActivity(intent);
    }
    
    public void onResume(){
    	super.onResume();
    	checkConn();
    	score = Gamification.getInstance(this.getApplicationContext());
        easyButton.setClickable(true);
        if (!score.isMediumLevelActivated()){
        	middleButton.setClickable(false);
        	middleButton.getBackground().setAlpha(50);
        }
        hardButton =(Button) findViewById(R.id.hardButton);
        if (!score.isExpertLevelActivated()){
        	hardButton.setClickable(false);
        	hardButton.getBackground().setAlpha(50);
        }
        int i = score.getScore();
        punktestandAnzeige.setText("" + i);
        levelAnzeige = (TextView) findViewById(R.id.LevelAnzeige);
        levelAnzeige.setText(score.getLevelName());
        
    }
    
    public AlertDialog createWarningDialog(){
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Warning");
		alertDialog.setMessage("Your internet connection is not available, please try again in a few minutes.");
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int which){
			   finish();
		   }
		});
		alertDialog.setIcon(R.drawable.warning);
		return alertDialog;
	}

	public void checkConn() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (!(netInfo != null && netInfo.isConnectedOrConnecting())) {
			if(alertDialog == null){
				alertDialog = createWarningDialog();
			}
			alertDialog.show();
		}

	}

    
}