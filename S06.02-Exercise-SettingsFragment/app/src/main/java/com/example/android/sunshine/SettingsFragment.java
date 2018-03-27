package com.example.android.sunshine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

/**
 * Created by I855677 on 2/28/2018.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_sunshine);

        PreferenceScreen preferenceScreen = getPreferenceScreen();
        SharedPreferences sharedPreferences = preferenceScreen.getSharedPreferences();

        int prefCount = preferenceScreen.getPreferenceCount();

        for(int i=0; i < prefCount; i++) {
            Preference preference = preferenceScreen.getPreference(i);

            if(!(preference instanceof  CheckBoxPreference)) {
                String value = sharedPreferences.getString(preference.getKey(), getString(R.string.pref_location_default));
                setPreferenceSummary(preference, value);
            }
        }


    }

    void setPreferenceSummary(Preference preference, String value) {

        if((preference instanceof ListPreference)) {
            ListPreference listPreference = (ListPreference) preference;

            // get index of value
           int index = listPreference.findIndexOfValue(value);
           preference.setSummary(listPreference.getEntries()[index]);
        }
        else if(preference instanceof EditTextPreference) {
            preference.setSummary(value);
        }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Preference preference = findPreference(key);

        if(null != preference) {
            if(!(preference instanceof  CheckBoxPreference)) {
                String value = sharedPreferences.getString(key, getString(R.string.pref_location_default));
                setPreferenceSummary(preference, value);
            }
        }
    }

    @Override
    public void onStart() {
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        super.onStart();
    }

    @Override
    public void onStop() {

        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onStop();
    }
}
