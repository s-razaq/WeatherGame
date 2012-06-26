package com.sample;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;


public class RandCity extends AsyncTask {
	
	private static AssetManager assetManager;
	private static ArrayList<String> easyList = new ArrayList<String>(), mediumList= new ArrayList<String>(), hardList= new ArrayList<String>();
	
	private static RandCity instance = null;
	public static RandCity getInstance(Context iAppContext) {
        if (instance == null) {
            instance = new RandCity(iAppContext);
        }
        return instance;
    }
	private RandCity(Context iAppContext) {
		assetManager = iAppContext.getAssets();
		
	}
	public void loadCSV() {
		fillArray("easy");
		fillArray("medium");
		fillArray("hard");
	}

	public String getCity(String iDifficulty) {
		
		String city = null;
		int rand = 0;
		if(iDifficulty.equals("easy")) {
			//for(int i = 0; i < easyList.size();i++) System.out.println(easyList.get(i));
			rand = (int) (Math.random() * (easyList.size()-1));
			city = easyList.get(rand);
			easyList.remove(rand);
			easyList.remove(city);
			//System.out.println(easyList.size());
			if(easyList.size() == 0) {
				fillArray(iDifficulty);
			}
		}
		else if(iDifficulty.equals("medium")) {
			rand = (int)(Math.random() * (mediumList.size()-1));
			city = mediumList.get(rand);
			mediumList.remove(rand);
			mediumList.remove(city);
			if(mediumList.size() == 0) fillArray(iDifficulty);
		}
		else {
			rand = (int)(Math.random() * (hardList.size()-1));
			city = hardList.get(rand);
			hardList.remove(rand);
			hardList.remove(city);
			if(hardList.size() == 0) fillArray(iDifficulty);
		}
		System.out.println(city);
		return city;
	}
	
	/*public String getLatLong(String iDifficulty) {
		if(iDifficulty == "easy" || iDifficulty == "medium") return getCity(iDifficulty);
		
		
		
		return "";
	}*/
	
	private void fillArray(String iDifficulty){
		
		InputStream stream = null;
		BufferedInputStream readbuffer = null; 
        byte[] buffer = new byte[100000];
        int bytesRead; 
        try {
            stream = assetManager.open(iDifficulty+".csv");
            
            readbuffer = new BufferedInputStream(stream);
            bytesRead = 0;
            String[] splitArrayLine = null;
            String[] splitArrayTab = null;
            
            while ((bytesRead = readbuffer.read(buffer)) != -1) {
              String rawCity = new String(buffer, 0, bytesRead);
              System.out.print(rawCity);
              splitArrayLine = rawCity.split("\n");
            }
            
            System.out.println(""+splitArrayLine.length);
            
            for(int i = 0; i < splitArrayLine.length; i++) {
            	splitArrayTab = splitArrayLine[i].split("\t");

            	if(iDifficulty.equals("easy")) easyList.add(splitArrayTab[1].toString()+";"+splitArrayTab[2].toString());
            	if(iDifficulty.equals("medium")) mediumList.add(splitArrayTab[1].toString()+";"+splitArrayTab[2].toString());
            	if(iDifficulty.equals("hard")) {
            		String lati = splitArrayTab[3].toString();
            		String longi =  splitArrayTab[4].toString();
            		longi = longi.substring(0, longi.length()-2);
            		hardList.add(lati + "," + longi + ";" + splitArrayTab[2].toString());
            	}
            }
            
        } catch (IOException e) {

            e.printStackTrace();// handle

        }
        finally {
            if (stream != null) {
                  try {
                      readbuffer.close();
                	  stream.close();
                  } catch (IOException e) {}
            }
        }
	}
	@Override
	protected Object doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
