package com.sample;



import kankan.wheel.widget.WheelView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


public class GameActivity extends Activity {
	
	TextView stadt;
	String difficulty;
	Button enterButton;
	WeatherService ws;
	static String city;
	static String rawCity;
	static String[] rawCities = new String[2];
	static String countryCode;
	WheelView temValue;
	int[] res;
	
	//method for getting a random city
	public void getNewCity() {
	    
        RandCity randCity = RandCity.getInstance(this.getApplicationContext());
        rawCity = randCity.getCity(difficulty);
        rawCities = rawCity.split(";");
        city = rawCities[0];
        countryCode = rawCities[1];
        stadt.setText(city);
	}
	//method for going back to BestWeatherGameEverActivity
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Aufbau des menues
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.game_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // selektion innerhalb des menüs (hilfe, reset)
	    switch (item.getItemId()) {
	        case R.id.reset:
	            gameReset();
	            return true;
	        case R.id.help:
	            showHelp();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	
	public void gameReset(){
		//zurücksetzen des spielstandes
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
			SharedPreferences.Editor editor = settings.edit();
			editor.putInt("score", 0);
			// setzt spielstand auf 0!
			editor.commit();
	}
	public void showHelp(){
		//hilfe activity laden.
	}
	 public void returnToBestWeatherGameEverActivity(){
     	
     	finish();
     }
	 
	 public int[] compareResult(){//parameter is actual number of the Wheeler
		 res = new int[3];
		 res[0] = ws.getTemperature(city); //get data from Webservice
		 res[1] = NumberClass.values[temValue.getCurrentItem()];
		 res[2] = Math.abs(res[0] - res[1]); //devation from gamification class
		 return res;
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
	        rawCity = randCity.getCity(difficulty);
	        rawCities = rawCity.split(";");
	        city = rawCities[0];
	        countryCode = rawCities[1];
	        stadt.setText(city);
	         
	        //creating wheeler
	        temValue = (WheelView) findViewById(R.id.value);
	        temValue.setViewAdapter(new NumberClass(this));
	        temValue.setCurrentItem(60);
	        
	        //Dialog
	        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
	        final View layout = inflater.inflate(R.layout.dialog, (ViewGroup) findViewById(R.id.dialogRoot));
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setView(layout);
	        builder.setMessage("Do you want to continue?")
	               .setCancelable(false)
	               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   getNewCity();
	                	   dialog.cancel();
	                   }
	               })
	               .setNegativeButton("Back to menu", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   
	                	   returnToBestWeatherGameEverActivity();
	                   }
	               });
			
	        final AlertDialog alert = builder.create();
	  
	        
	        //activate enterButton
	        enterButton.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					if(v.getId() == R.id.enterButton) {
						Gamification game = Gamification.getInstance(getApplicationContext());
						int[] results = compareResult();
						game.newScoreForResult(results[2]);
						System.out.println(game.getScore());
				       
						// Get instances for textviews on dialog
				        final TextView userAnswer = (TextView) layout.findViewById(R.id.userAnswer);
				        final TextView curTemp = (TextView) layout.findViewById(R.id.currentTemperature);
				        final TextView devLabel = (TextView) layout.findViewById(R.id.deviation);
				        final TextView scoreLabel = (TextView) layout.findViewById(R.id.currentPoints);
				        
				        // Get instance for RatingBar on diaglog
				        final RatingBar rb = (RatingBar) layout.findViewById(R.id.ratingBar1);
				       
				        // Calculating value for rating bar
				        float ratingValue = (float) (5 - (0.5*res[2]));
				        if(ratingValue < 0){
				        	ratingValue = 0;
				        }
				        
				       // Set rating on Dialog
				        rb.setRating(ratingValue);
				        rb.setEnabled(false);
				          			  		
						// Set userAnswer on Dialog
						userAnswer.setText(""+results[1]);
				        
				        // Set CurrentTemp on Dialog
				        curTemp.setText(""+results[0]);

				        // Set deviation on Dialog
				        devLabel.setText(""+results[2]);
				        
				        // Set score on Dialog
				        scoreLabel.setText(""+game.getScore());
				        
						alert.show();
					}
				}
	        	
	        });
	        // little popup to get the country of the city
	        TextView cityLabel = (TextView) findViewById(R.id.stadt);
	        cityLabel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(v.getId() == R.id.stadt) {
						int duration = Toast.LENGTH_LONG;
						CharSequence text = city+" is located in " + countryCode;
						System.out.println(text);
						Toast toast = Toast.makeText(getApplicationContext(), text, duration);
						toast.show();						
					}
				}
			});
	        
	        
	   
	        
	   }
}
