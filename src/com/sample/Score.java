package com.sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;

public class Score {
	int score = 0;
	String filename = "score";
	public Score(int x){
		score = x;
		File file = new File(Environment.getExternalStorageDirectory(), filename);
		if (!file.exists()){
		this.save();
		}else{
		try {
		    InputStream instream = openFileInput(this.filename);
		    if (instream) {
		      // prepare the file for reading
		      InputStreamReader inputreader = new InputStreamReader(instream);
		      BufferedReader buffreader = new BufferedReader(inputreader);
		      String line = buffreader.readLine();
		      score = Integer.parseInt(line);
		      }
		    instream.close();
		 
		} catch (Exception e) {
		    // handle exception
		}
		
		}
	}
	public int getScore(){
		return score;
	
	}
	public void setScore(int x){
		score = x;
		this.save()
	}
	
	private void save(){
		File file = new File(Environment.getExternalStorageDirectory(), this.filename);
		FileOutputStream fos;
		try {
		    fos = new FileOutputStream(file);
		    fos.write(score.toString());
		    fos.flush();
		    fos.close();
		} catch (Exception e) {
		    // handle exception
		}
	
		
		
}
