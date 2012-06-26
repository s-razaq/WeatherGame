package com.sample;



import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;


public class GameActivity extends Activity {
	
	TextView stadt;
	String difficulty;
	Button enterButton;
	WeatherService ws;
	static String city;
	WheelView temValue;
	
	public void getNewCity() {
	    
        RandCity randCity = RandCity.getInstance(this.getApplicationContext());
        city = randCity.getCity(difficulty);
        stadt.setText(city);
	}
	
	 public void returnToBestWeatherGameEverActivity(){
     	
     	finish();
     }
	 
	 public int compareResult(){//Parameter ist die aktuelle Einstellung des Wheels
		 int ergebnis = ws.getTemperature(city); //Webservice Daten holen
		 int deviation;
		 deviation = Math.abs(ergebnis - NumberClass.values[temValue.getCurrentItem()]); //Abweichung f�r Gamification
		 return deviation;
	 }
	
	public void onCreate (Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.play_screen);
	        ws = WeatherService.getInstance();
	        
	        final Intent intent = this.getIntent();

	        difficulty = intent.getStringExtra("difficulty");
	        System.out.println(difficulty);
	        
	        stadt = (TextView) findViewById(R.id.stadt);
	        enterButton =(Button) findViewById(R.id.enterButton);
	        
	        //Random City
	        RandCity randCity = RandCity.getInstance(this.getApplicationContext());
	        city = randCity.getCity(difficulty);
	        stadt.setText(city);
	         
	        temValue = (WheelView) findViewById(R.id.value);
	        temValue.setViewAdapter(new NumberClass(this));
	        temValue.setCurrentItem(60);
	        
	        //Dialog
	        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
	        View layout = inflater.inflate(R.layout.dialog, (ViewGroup) findViewById(R.id.dialogRoot));
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setView(layout);
	        builder.setMessage("Do you want to continue?")
	               .setCancelable(false)
	               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   //hier muss Methode zum Punkte berechnen aufgerufen werden
	                	   
	                	   //Show new City
	                	   getNewCity();
	                	   dialog.cancel();
	                   }
	               })
	               .setNegativeButton("Back to menu", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   //hier muss Methode zum Punkte berechnen aufgerufen werden
	                	   
	                	   returnToBestWeatherGameEverActivity();
	                   }
	               });
	        final AlertDialog alert = builder.create();
	        
	        
	        //PopUp 
	        //PopupWindow evaluationPopup = new PopupWindow();
	        
	        //activate enterButton
	        enterButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Gamification game = Gamification.getInstance(getApplicationContext());
					game.newScoreForResult(compareResult());
					System.out.println(game.getScore());
					alert.show();
				}
	        	
	        });
	   
	        
	   }
}
