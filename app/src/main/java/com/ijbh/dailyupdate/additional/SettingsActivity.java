package com.ijbh.dailyupdate.additional;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ijbh.dailyupdate.ui.SettingsFragment;

public class SettingsActivity extends Activity {


    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().add(android.R.id.content, new SettingsFragment()).commit();
    }


}
