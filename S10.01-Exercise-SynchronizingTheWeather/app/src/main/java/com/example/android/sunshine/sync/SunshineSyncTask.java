package com.example.android.sunshine.sync;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.example.android.sunshine.data.SunshinePreferences;
import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.data.WeatherProvider;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

//  TODO (1) Create a class called SunshineSyncTask
//  TODO (2) Within SunshineSyncTask, create a synchronized public static void method called syncWeather
//      TODO (3) Within syncWeather, fetch new weather data
//      TODO (4) If we have valid results, delete the old data and insert the new
public class SunshineSyncTask {

    synchronized public static void syncWeather(Context context) {

        String location = SunshinePreferences.getPreferredWeatherLocation(context);

            URL url = NetworkUtils.getUrl(context);

        try {
            String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(url);

            ContentValues[] simpleWeatherResponse = OpenWeatherJsonUtils.getWeatherContentValuesFromJson(context, jsonWeatherResponse);

            if(jsonWeatherResponse != null) {
                boolean deletedSuccessfull = deleteOldDataFromDB(context);

//                if(deletedSuccessfull) {
                    int numberOfRecordsInserted = insertNewDataInDB(context, simpleWeatherResponse);
//                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static int insertNewDataInDB(Context context, ContentValues[] simpleWeatherResponse) {

        Uri uri = WeatherContract.WeatherEntry.CONTENT_URI;

        int numberOfRecordsInserted = context.getContentResolver().bulkInsert(uri, simpleWeatherResponse);

        return numberOfRecordsInserted;
    }

    private static boolean deleteOldDataFromDB(Context context) {
        Uri uri = WeatherContract.WeatherEntry.CONTENT_URI;

        int numberOfRecordsDeleted = context.getContentResolver().delete(uri,
                null,
                null);

        if(numberOfRecordsDeleted > 0)
            return true;

        return false;
    }
}