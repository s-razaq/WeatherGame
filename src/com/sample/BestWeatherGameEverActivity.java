package com.sample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class BestWeatherGameEverActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);
        Button easyButton =(Button) findViewById(R.id.easyButton);
        Button middleButton =(Button) findViewById(R.id.middleButton);
        Button hardButton =(Button) findViewById(R.id.hardButton);
        TextView punktestandAnzeige = (TextView) findViewById(R.id.PunktestandAnzeige);
        TextView levelAnzeige = (TextView) findViewById(R.id.LevelAnzeige);
    }
}