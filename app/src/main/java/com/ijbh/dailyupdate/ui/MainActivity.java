package com.ijbh.dailyupdate.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.ijbh.dailyupdate.R;
import com.ijbh.dailyupdate.additional.SettingsActivity;
import com.ijbh.dailyupdate.additional.UpdateReceiver;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NewsFragment.OnNewsFragmentListener, WeatherFragment.OnWeatherFragmentListener{

    private static final int SETTINGS_REQUEST = 99;
    private static final int DEFAULT_INTERVAL = 60;
    final String NEWS_FRAGMENT_TAG = "news_fragment";
    final String WEATHER_FRAGMENT_TAG = "weather_fragment";
    final String CHANNEL_NEWS_ID = "news_channel";
    final String CHANNEL_WEATHER_ID = "weather_channel";
    final CharSequence CHANNEL_NEWS_NAME = "News Channel";
    final CharSequence CHANNEL_WEATHER_NAME = "Weather Channel";

    NotificationManager manager;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannels();
        int interval = DEFAULT_INTERVAL;
        try{
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            interval = Integer.parseInt(sp.getString("preferences_notification_interval", "10"));
            Toast.makeText(this, "interval: " + interval, Toast.LENGTH_SHORT).show();

        }catch (NullPointerException npe){
            Toast.makeText(this, "no sp - interval: " + interval, Toast.LENGTH_SHORT).show();

        }
        finally {
            startNotifications(interval);
        }

        //fragments instances
        NewsFragment newsFragment = NewsFragment.newInstance();
        WeatherFragment weatherFragment = WeatherFragment.newInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.news_container, newsFragment, NEWS_FRAGMENT_TAG);
        transaction.add(R.id.weather_container, weatherFragment, WEATHER_FRAGMENT_TAG);
        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.general_settings){
            startActivityForResult(new Intent(this, SettingsActivity.class), SETTINGS_REQUEST);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SETTINGS_REQUEST){

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

            int interval = Integer.parseInt(sp.getString("preferences_notification_interval", "10"));
            //int interval = sp.getInt("interval_list_values", 10);
            //startNotifications(interval);
            Toast.makeText(this, "interval updated: " + interval, Toast.LENGTH_SHORT).show();

        }
    }

    private void startNotifications(int interval) {

        //alarm manager

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, interval);

        Intent intent = new Intent(MainActivity.this, UpdateReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(MainActivity.this, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);

    }

    private void createNotificationChannels() {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= 26){
            manager.createNotificationChannel(new NotificationChannel(CHANNEL_NEWS_ID, CHANNEL_NEWS_NAME, NotificationManager.IMPORTANCE_HIGH));
            manager.createNotificationChannel(new NotificationChannel(CHANNEL_WEATHER_ID, CHANNEL_WEATHER_NAME, NotificationManager.IMPORTANCE_DEFAULT));
        }

    }


}
