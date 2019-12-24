package com.ijbh.dailyupdate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.ijbh.dailyupdate.fragments.NewsFragment;
import com.ijbh.dailyupdate.fragments.WeatherFragment;

public class MainActivity extends AppCompatActivity implements NewsFragment.OnNewsFragmentListener{

    final String NEWS_FRAGMENT_TAG = "news_fragment";
    final String WEATHER_FRAGMENT_TAG = "weather_fragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NewsFragment newsFragment = NewsFragment.newInstance();
        WeatherFragment weatherFragment = WeatherFragment.newInstance();

        //FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.news_container, newsFragment, NEWS_FRAGMENT_TAG);
        transaction.add(R.id.weather_container, weatherFragment, WEATHER_FRAGMENT_TAG);
        transaction.addToBackStack(null);
        transaction.commit();


    }


    @Override
    public void onBackPressed() {

        if(getFragmentManager().getBackStackEntryCount()>0) {
            getFragmentManager().popBackStack();
        }
        else super.onBackPressed();
    }
}
