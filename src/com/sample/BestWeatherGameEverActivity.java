package com.sample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        //create objects with references
        score = Gamification.getInstance(this.getApplicationContext());
        easyButton =(Button) findViewById(R.id.easyButton);
        easyButton.setClickable(true);
        //middleButton is not clickable without enough points
        middleButton =(Button) findViewById(R.id.middleButton);
        if (!score.isMediumLevelActivated()){
        	middleButton.setClickable(false);
        	middleButton.getBackground().setAlpha(50);
        }
        //hardButton is not clickable without enough points
        hardButton =(Button) findViewById(R.id.hardButton);
        if (!score.isExpertLevelActivated()){
        	hardButton.setClickable(false);
        	hardButton.getBackground().setAlpha(50);
        }
        punktestandAnzeige = (TextView) findViewById(R.id.PunktestandAnzeige);
        int i = score.getScore();
        punktestandAnzeige.setText("Score: " + i);
        levelAnzeige = (TextView) findViewById(R.id.LevelAnzeige);
        levelAnzeige.setText("Level: "+ score.getLevelName());
        
        //loading CSV with cities
        RandCity randCity = RandCity.getInstance(this.getApplicationContext());
        randCity.loadCSV();     
       
    }
    
    //actionListner for EasyButton
    public void onClickEasy(final View myView) {
    	final Intent intent = new Intent(this, GameActivity.class);
    	intent.putExtra("difficulty", DIFF_EASY);
    	startActivity(intent);
    }
    //actionListner for middleButton
    public void onClickMedium(final View myView) {
    	final Intent intent = new Intent(this, GameActivity.class);
    	intent.putExtra("difficulty", DIFF_MEDIUM);
    	startActivity(intent);
    }
    //actionListner for hardButton
    public void onClickHard(final View myView) {
    	final Intent intent = new Intent(this, GameActivity.class);
    	intent.putExtra("difficulty", DIFF_HARD);
    	startActivity(intent);
    }
    
    //method for going back to the BestWeatherGameEverActivity
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
        punktestandAnzeige.setText("Score: " + i);
        levelAnzeige = (TextView) findViewById(R.id.LevelAnzeige);
        levelAnzeige.setText("Level: "+ score.getLevelName());
        
    }
    //method with a dialog when user loses internet connection
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
    //method for checking internet connection
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Build menu
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.game_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Selection within menu (help, reset)
	    switch (item.getItemId()) {
	        case R.id.reset:
	            gameReset();
	            return true;
	        case R.id.help:
	        	InstructionsDialog();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	public void gameReset(){
        score.gameReset();
        punktestandAnzeige.setText("Score: 0");
        levelAnzeige = (TextView) findViewById(R.id.LevelAnzeige);
        levelAnzeige.setText("Level: "+ score.getLevelName());
	}
	
	public void InstructionsDialog(){

		  AlertDialog.Builder ad = new AlertDialog.Builder(this);
		  ad.setIcon(R.drawable.icon);
		  ad.setTitle("Help ...");
		  ad.setView(LayoutInflater.from(this).inflate(R.layout.help_dialog,null));
		  ad.setCancelable(false);
		  ad.setPositiveButton("OK", 
		    new android.content.DialogInterface.OnClickListener() {
		     public void onClick(DialogInterface dialog, int arg1) {
		      // OK, go back to Main menu
		     }
		    }
		   );
		  AlertDialog alert = ad.create();
		  alert.show();
		 }

    
}