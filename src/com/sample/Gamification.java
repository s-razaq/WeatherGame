package com.sample;

public class Gamification {
	private static Gamification instance = null;
	private int score = 0;
	private String[] levelDescription = {"Total Beginner", "Weather Noob", "Estimator", "Weather Expert", "Master Forecaster", "Weather Wizard"};
	private int[] pointsNeededForLevel = {0,40,100,200,350,555};

	 public static Gamification getInstance() {
	        if (instance == null) {
	            instance = new Gamification();
	        }
	        return instance;
	    }
	
	private Gamification(){
		//score = aus Settings laden
		
	}
	
	
	public int newScoreForResult(int deviation){
		int level = this.calculateLevel();
		int newScore = score + 12-level-deviation;
		System.out.println("new score:" + score);	
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
