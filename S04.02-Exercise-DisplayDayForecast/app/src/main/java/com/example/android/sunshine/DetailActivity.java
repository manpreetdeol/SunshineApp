package com.example.android.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";

    private TextView weatherDetailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intentReceived = getIntent();

        if(intentReceived.hasExtra(Intent.EXTRA_TEXT)) {
            String weatherData = intentReceived.getStringExtra(Intent.EXTRA_TEXT);
            weatherDetailTextView.setText(weatherData);
        }

        weatherDetailTextView = (TextView) findViewById(R.id.tv_weather_detail);


        // TODO (2) Display the weather forecast that was passed from MainActivity
    }
}