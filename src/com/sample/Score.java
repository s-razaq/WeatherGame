package com.sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;

public class Score {
	
	public Score(){
		String filename = "score.scr";
		Integer scor;
		File file = new File(Environment.getExternalStorageDirectory(), filename);
		if (!file.exists()){
		FileOutputStream fos;
		try {
		    fos = new FileOutputStream(file);
		    fos.write(0);
		    fos.flush();
		    fos.close();
		} catch (FileNotFoundException e) {
		    // handle exception
		} catch (IOException e) {
		    // handle exception
		}
	}else{
		
	}
		
	}
}
