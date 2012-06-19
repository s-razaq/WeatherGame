package com.sample;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Gamification extends Activity{
	private static Gamification instance = null;
	private int score = 0;
	private String[] levelDescription = {"Total Beginner", "Weather Noob", "Estimator", "Weather Expert", "Master Forecaster", "Weather Wizard"};
	private int[] pointsNeededForLevel = {0,40,100,200,350,555};
    private SharedPreferences settings;

	 public static Gamification getInstance() {
	        if (instance == null) {
	            instance = new Gamification();
	        }
	        return instance;
	    }
////
////	private Gamification(){
////		//score = aus Settings laden
////	      settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
////	      if(!settings.contains("score")){
////		      SharedPreferences.Editor editor = settings.edit();
////		      editor.putInt("score", 0);
////		      // Commit the edits!
////		      editor.commit();
////	      } else {
////	    	  score = settings.getInt("score",0);
////	      }
//
//
//
//		
//	}
	
	
	public int newScoreForResult(int deviation){
		int level = this.calculateLevel();
		int newScore = score + 12-level-deviation;
		
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putInt("score", newScore);
	      editor.commit();
		
		System.out.println("new score:" + newScore);	
		//check if new level was reached
		for(int i = 1;i<=pointsNeededForLevel.length;i++){
			if (score < pointsNeededForLevel[i-1] && pointsNeededForLevel[i-1] <= newScore){
				//new level reached
				
				//todo: throw toast message
				
				break;
			} 
		}
		return score = newScore;
	}
	
	public int getLevel(){
		return this.calculateLevel();
	}
	
	public boolean isMediumLevelActivated(){
		return (this.calculateLevel()>2);
	}
	
	public boolean isExpertLevelActivated(){
		return (this.calculateLevel()>4);
	}
	
	public String getLevelName(){
		return levelDescription[getLevel()-1];
	}
	
	public int getScore(){
		return score;
	}
	
	private int calculateLevel(){
		int level = 0;
		for(int i = 1;i<=levelDescription.length;i++){
			if (score < pointsNeededForLevel[i-1]){
				level = i-1;
				break;
			} else {
				level = i;
			}
		}
		System.out.println("current level: " + level + " " + levelDescription[level-1]);
		return level;
	}
	
	
}
