package com.ijbh.dailyupdate.ui;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import androidx.annotation.Nullable;

import com.ijbh.dailyupdate.R;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
