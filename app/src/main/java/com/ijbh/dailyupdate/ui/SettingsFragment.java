package com.ijbh.dailyupdate.ui;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ijbh.dailyupdate.R;

public class SettingsFragment extends PreferenceFragment {
    private CheckBoxPreference allCheck;
    private CheckBoxPreference newsCheck;
    private CheckBoxPreference weatherCheck;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        allCheck = (CheckBoxPreference) findPreference("preference_all_notifications");
        newsCheck = (CheckBoxPreference) findPreference("preference_news_notifications");
        weatherCheck = (CheckBoxPreference) findPreference("preference_weather_notifications");

        allCheck.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final boolean value = (boolean) newValue;
                newsCheck.setChecked(value);
                weatherCheck.setChecked(value);

                return true;
            }
        });

        newsCheck.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final boolean value = (boolean) newValue;
                Log.d("Checkbox", "all: " + allCheck.isChecked() + " news: " + newsCheck.isChecked() + " weather: " + weatherCheck.isChecked());

                if(value == true && weatherCheck.isChecked())
                    allCheck.setChecked(value);
                else if (value == false && !weatherCheck.isChecked())
                    allCheck.setChecked(value);
                return true;
            }
        });
        weatherCheck.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final boolean value = (boolean) newValue;
                Log.d("Checkbox", "all: " + allCheck.isChecked() + " news: " + newsCheck.isChecked() + " weather: " + weatherCheck.isChecked());

                if(value == true && newsCheck.isChecked())
                    allCheck.setChecked(value);
                else if (value == false && !newsCheck.isChecked())
                    allCheck.setChecked(value);
                return true;
            }
        });
    }


}
