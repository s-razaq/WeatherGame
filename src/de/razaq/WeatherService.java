package de.razaq;

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

/**
 * This class enables developers to retrieve weather information for a particular location.
 * The location can be passed as a city name or as a pair of [latitude, longitude] value.
 * @author paki
 */
public class WeatherService {

	/* Variables that are mandatory for handling client-server interaction */
	static HttpClient client;
	private HttpGet request;
	private HttpResponse response;

	/* Variables that store information about service url and parameters that are meadatory */
	private final String BASE_URL = "http://api.worldweatheronline.com/free/v1/weather.ashx";
	private final String KEY = "7mnypztddtcsvzp69uny4vse";
	private final String FORMAT = "json";
	private String requestValue = "Karlsruhe";
	private String urlString;

	private StringBuilder jsonResponse;

	/* Classical implementation of Singleton-Pattern */
	private static WeatherService instance = null;

	/**
	 * Default constructor. Cannot be called from outside
	 */
	private WeatherService() {
	}

	/**
	 * Static method that returns the only instance of this class
	 */
	public static WeatherService getInstance() {
		if (instance == null) {
			instance = new WeatherService();
			client = new DefaultHttpClient();
		}
		return instance;
	}

	/**
	 * Returns the current temperature for any given city
	 * @param Cityname as string . 
	 * @return Temperature for given city as interger
	 */
	public int getTemperature(String city) {
		requestValue = city;
		postRequest();
		return parseJSON(jsonResponse.toString());
	}
	
	
	/**
	 * Returns the current temperature for any given value pair
	 * @param latitude
	 * @param longitude
	 * @return Temperature for given city as interger
	 */
	public double getTemperature(double latitude, double longitude){
		requestValue = String.valueOf(latitude) + "," + String.valueOf(longitude);
		postRequest();
		return parseJSON(jsonResponse.toString());	
	}

	/**
	 * Concianate various variables that contain essential information such as url, request value and return format
	 */
	private void createUrlString() {
		urlString = BASE_URL + "?key=" + KEY + "&q=" + Uri.encode(requestValue)
				+ "&format=" + FORMAT;
	}

	
	/**
	 * This method is reponsible for the client-server interaction.
	 * For this, it excecute a HttpGet request and handles the reponse from server
	 */
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

	/**
	 * Try to parse any given JSON and find a specific value 
	 * @param JSON as String
	 * @return  Current temperature as integer
	 */
	private int parseJSON(String value) {
		/* Variable is initialied with a spefic value.
		 * 404 represent a error code and should indicate that something went wrong parsing response value
		 */
		int d = 404;
		try {
			/* The reponse json has following strucutre: {[],[],[]}
			 * For us, only the object with the key "current_condition" is important
			 */
			JSONObject jsonObj = new JSONObject(value).getJSONObject("data")
					.getJSONArray("current_condition").getJSONObject(0);
			/* Each value in this JSON is represented via String. That's why we need to parse it */
			d = Integer.parseInt(jsonObj.getString("temp_C"));
			/* Logs certain values from JSON that can be useful when debuggin */
			Log.i("BestWeatherGame", "Temp_C in " + requestValue + " = " + d);
			Log.i("BestWeatherGame", "Observation_time" + " = " + jsonObj.getString("observation_time"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}

}
