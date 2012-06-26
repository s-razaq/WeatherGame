package com.sample;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class Gamification extends Activity {
	private static Gamification instance = null;
	private int score = 0;
	private String[] levelDescription = { "Total Beginner", "Weather Noob",
			"Estimator", "Weather Expert", "Master Forecaster",
			"Weather Wizard" };
	private int[] pointsNeededForLevel = { 0, 40, 100, 200, 350, 555 };
	private SharedPreferences settings;
	private Context ctx;

	public static Gamification getInstance(Context ctx) {

		if (instance == null) {
			instance = new Gamification(ctx);
		}
		return instance;
	}

	private Gamification(Context ctx) {
		this.ctx = ctx;
		// score = aus Settings laden######
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		if (!settings.contains("score")) {
			SharedPreferences.Editor editor = settings.edit();
			editor.putInt("score", 0);
			// Commit the edits!
			editor.commit();
		} else {
			score = settings.getInt("score", 0);
		}
	}

	public int newScoreForResult(int deviation) {
		int level = this.calculateLevel();
		int newScore = score + 12 - level - deviation;

		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("score", newScore);
		editor.commit();

		System.out.println("new score:" + newScore);
		// check if new level was reached
		for (int i = 1; i <= pointsNeededForLevel.length; i++) {
			if (score < pointsNeededForLevel[i - 1]
					&& pointsNeededForLevel[i - 1] <= newScore) {
				// new level reached
				int duration = Toast.LENGTH_LONG;
				score = newScore;
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
					&& pointsNeededForLevel[i - 1] > newScore) {
				int duration = Toast.LENGTH_LONG;
				score = newScore;
				CharSequence text = "Oh no! You lost too many points. Your new level is " + this.getLevelName();
				System.out.println(text);
				Toast toast = Toast.makeText(this.ctx, text, duration);
				toast.show();						
				break;
			}
		}
			
		return score = newScore;
	}

	public int getLevel() {
		return this.calculateLevel();
	}

	public boolean isMediumLevelActivated() {
		return (this.calculateLevel() > 2);
	}

	public boolean isExpertLevelActivated() {
		return (this.calculateLevel() > 4);
	}

	public String getLevelName() {
		return levelDescription[getLevel() - 1];
	}

	public int getScore() {
		return score;
	}

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

}
