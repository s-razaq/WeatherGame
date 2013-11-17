package de.razaq;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;


public class RandCity {

	private static AssetManager assetManager;
	private static ArrayList<String> easyList = new ArrayList<String>(), mediumList= new ArrayList<String>(), hardList= new ArrayList<String>();

	/*
	 * Singleton Pattern
	 */
	private static RandCity instance = null;
	public static RandCity getInstance(Context iAppContext) {
		if (instance == null) {
			instance = new RandCity(iAppContext);
		}
		return instance;
	}
	private RandCity(Context iAppContext) {
		// Assign App Context to local static variable
		assetManager = iAppContext.getAssets();
		
	}
	
	/*
	 *  Initially Fills ArrayLists
	 */
	public void loadCSV() {
		fillArray("easy");
		fillArray("medium");
		fillArray("hard");
	}

	/*
	 * Access the ArrayList from other classes
	 */
	public String getCity(String iDifficulty) {

		String city = null;
		int rand = 0;
		if(iDifficulty.equals("easy")) {
			// Generates random access key
			rand = (int) (Math.random() * (easyList.size()-1));
			city = easyList.get(rand);
			// Assures that random number is removed from ArrayList
			easyList.remove(rand);
			easyList.remove(city);
			// Automatically refills ArrayList in case it is empty
			if(easyList.size() == 0) {
				fillArray(iDifficulty);
			}
		}
		else if(iDifficulty.equals("medium")) {
			rand = (int)(Math.random() * (mediumList.size()-1));
			city = mediumList.get(rand);
			mediumList.remove(rand);
			mediumList.remove(city);
			if(mediumList.size() == 0) {
				fillArray(iDifficulty);
			}	
		}
		else {
			rand = (int)(Math.random() * (hardList.size()-1));
			city = hardList.get(rand);
			hardList.remove(rand);
			hardList.remove(city);
			if(hardList.size() == 0) {
				fillArray(iDifficulty);
			}
		}
		System.out.println(city);
		return city;
	}

	/*
	 * Read CSV Tables
	 */
	private void fillArray(String iDifficulty){

		// Initiate Streams
		InputStream stream = null;
		BufferedInputStream readbuffer = null; 
		byte[] buffer = new byte[100000];
		int bytesRead; 
		try {
			// Open File
			stream = assetManager.open(iDifficulty+".csv");

			readbuffer = new BufferedInputStream(stream);
			bytesRead = 0;
			String[] splitArrayLine = null;
			String[] splitArrayTab = null;

			// Read File row-wise
			while ((bytesRead = readbuffer.read(buffer)) != -1) {
				String rawCity = new String(buffer, 0, bytesRead);
				System.out.print(rawCity);
				splitArrayLine = rawCity.split("\n");
			}

			System.out.println(""+splitArrayLine.length);
			
			// Add rows to corresponding ArrayList
			for(int i = 0; i < splitArrayLine.length; i++) {
				splitArrayTab = splitArrayLine[i].split("\t");

				if(iDifficulty.equals("easy")) {
					easyList.add(splitArrayTab[1].toString()+";"+splitArrayTab[2].toString());
				}
				if(iDifficulty.equals("medium")) {
					mediumList.add(splitArrayTab[1].toString()+";"+splitArrayTab[2].toString());
				}
				if(iDifficulty.equals("hard")) {
					String lati = splitArrayTab[3].toString();
					String longi =  splitArrayTab[4].toString();
					longi = longi.substring(0, longi.length()-2);
					hardList.add(lati + "," + longi + ";" + splitArrayTab[2].toString());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
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
}