package com.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.net.Uri;
import android.util.Log;

public class WeatherService {

	static HttpClient client;
	private HttpGet request;
	private HttpResponse response;

	private final String BASE_URL = "http://free.worldweatheronline.com/feed/weather.ashx";
	private final String KEY = "adaf549abd120300122505";
	private final String FORMAT = "json";
	private String requestValue = "Karlsruhe";
	private String urlString;

	private StringBuilder jsonResponse;

	/* Klassische Implementierung des Singleton-Patterns */
	private static WeatherService instance = null;

	/**
	 * Default-Konstruktor, der nicht außerhalb dieser Klasse aufgerufen werden
	 * kann
	 */
	private WeatherService() {
	}

	/**
	 * Statische Methode, liefert die einzige Instanz dieser Klasse zurück
	 */
	public static WeatherService getInstance() {
		if (instance == null) {
			instance = new WeatherService();
			client = new DefaultHttpClient();
		}
		return instance;
	}

	public int getTemperature(String city) {
		requestValue = city;
		postRequest();
		return parseJSON(jsonResponse.toString());
	}
	
	
	public double getTemperature(double latitude, double longitude){
		requestValue = String.valueOf(latitude) + "," + String.valueOf(longitude);
		postRequest();
		return parseJSON(jsonResponse.toString());	
	}

	private void createUrlString() {
		urlString = BASE_URL + "?key=" + KEY + "&q=" + Uri.encode(requestValue)
				+ "&format=" + FORMAT;
	}

	private void postRequest() {
		try {
			createUrlString();
			System.out.println(urlString);
			request = new HttpGet(urlString);
			response = client.execute(request);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				jsonResponse = new StringBuilder();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));
				String line;
				while ((line = br.readLine()) != null) {
					jsonResponse.append(line);
				}
			} else {
				Log.e("BestWeatherGame", "Failed to download file");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private int parseJSON(String value) {
		int d = 404;
		try {
			JSONObject jsonObj = new JSONObject(value).getJSONObject("data")
					.getJSONArray("current_condition").getJSONObject(0);
			d = Integer.parseInt(jsonObj.getString("temp_C"));
			Log.i("BestWeatherGame", "Temp_C in " + requestValue + " = " + d);
			Log.i("BestWeatherGame", "Observation_time" + " = " + jsonObj.getString("observation_time"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}

}
