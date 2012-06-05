package com.sample;

public class TestGamification {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Gamification gamification = Gamification.getInstance();
		for(int i = 0; i<1000; i++){
			gamification.newScoreForResult(5);
		}
		
		

	}

}
