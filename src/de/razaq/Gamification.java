package de.razaq;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class Gamification extends Activity {
	private static Gamification instance = null;
	private int score = 0;
	//in order to change levels and points needed for specific level change the arrays levelDescription and pointsNeededForLevel
	//there need to be as many descriptions as numbers (item count of array description needs to be the same as points needed for level)
	private String[] levelDescription = { "Total Beginner", "Weather Noob",
			"Estimator", "Weather Expert", "Master Forecaster",
			"Weather Wizard" };
	private int[] pointsNeededForLevel = { 0, 40, 100, 200, 350, 555 };
	private Context ctx; //application context, needed for SharedPreferences and ToastMessages. Reference is given from external in the Singleton method.

	public static Gamification getInstance(Context ctx) {
		//Singleton Pattern, creates an instance if no instance exists yet
		if (instance == null) {
			instance = new Gamification(ctx);
		}
		return instance;
	}

	private Gamification(Context ctx) {
		this.ctx = ctx;
		// load score from SharedPreferences (Settings). This allows us to persist the user score in the Preferences so that scores don't get lost if the app is closed.
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		if (!settings.contains("score")) {  //check if score is not created in SharedPreferences yet
			SharedPreferences.Editor editor = settings.edit();
			editor.putInt("score", 0); //if so, create it
			editor.commit(); // Commit the edit
		} else { //otherwise we load the existing score.
			score = settings.getInt("score", 0);
		}
	}

	public int newScoreForResult(int deviation) {
		int level = this.calculateLevel();
		int newScore = score + 11 - level - deviation; //calculation for the new score of the player

		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("score", newScore); //save new score in SharedPreferences
		editor.commit();

		System.out.println("new score:" + newScore);
		// check if new level was reached
		for (int i = 1; i <= pointsNeededForLevel.length; i++) {
			if (score < pointsNeededForLevel[i - 1]
					&& pointsNeededForLevel[i - 1] <= newScore) {
				// new level reached
				int duration = Toast.LENGTH_LONG;
				score = newScore;
				//throw a toast message to inform the user
				CharSequence text = "Congratulations! You reached the level " + this.getLevelName();
				System.out.println(text);
				Toast toast = Toast.makeText(this.ctx, text, duration);
				toast.show();						
				break;
			}
		}
		//check if player decreased one level
		for (int i = 1; i <= pointsNeededForLevel.length; i++) {
			if (score >= pointsNeededForLevel[i - 1]
					&& pointsNeededForLevel[i - 1] > newScore) { //player decreased one lebel
				int duration = Toast.LENGTH_LONG;
				score = newScore;
				//throw a toast message to inform the user
				CharSequence text = "Oh no! You lost too many points. Your new level is " + this.getLevelName();
				System.out.println(text);
				Toast toast = Toast.makeText(this.ctx, text, duration);
				toast.show();						
				break;
			}
		}
			
		return score = newScore;
	}
	
	//getter methods

	public int getLevel() {
		return this.calculateLevel();
	}
	
	public String getLevelName() {
		return levelDescription[getLevel() - 1];
	}

	public int getScore() {
		return score;
	}
	
	//this methods show, whether the Buttons Medium or Expert are grayed out. They should be grayed out if a player did not reach a certain level yet.

	public boolean isMediumLevelActivated() {
		//the medium level is activated if the user has reached level 3 (at least)
		return (this.calculateLevel() > 2); 
	}

	public boolean isExpertLevelActivated() {
		 //the expert level is activated if the user has reached level 5 (at least)
		return (this.calculateLevel() > 4);
	}


	//private method for calculating the current level of a user
	
	private int calculateLevel() {
		int level = 0;
		for (int i = 1; i <= levelDescription.length; i++) {
			if (score < pointsNeededForLevel[i - 1]) {
				if(score<0) {
					level = 1;
				}
				else {
					level = i - 1;
				}
				break;
			} else {
				level = i;
			}
		}
		System.out.println("current level: " + level + " "
				+ levelDescription[level - 1]);
		return level;
	}
	
	public void gameReset(){
		// Reset Score
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(ctx);
			SharedPreferences.Editor editor = settings.edit();
			editor.putInt("score", 0);
			// Sets score to zero!
			editor.commit();
			score = 0;
	}

}
