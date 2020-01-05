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
import android.util.Log;
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
    private static final boolean DEFAULT_SEND_ALL = true;
    private static final boolean DEFAULT_SEND_NEWS = true;
    private static final boolean DEFAULT_SEND_WEATHER = true;
    final String NEWS_FRAGMENT_TAG = "news_fragment";
    final String WEATHER_FRAGMENT_TAG = "weather_fragment";
    final String CHANNEL_NEWS_ID = "news_channel";
    final String CHANNEL_WEATHER_ID = "weather_channel";
    final CharSequence CHANNEL_NEWS_NAME = "News Channel";
    final CharSequence CHANNEL_WEATHER_NAME = "Weather Channel";
    final int NOTIF_NEW_ARTICLE_ID = 1;
    final int NOTIF_LATEST_NEWS_ID = 2;
    final int NOTIF_NEW_WEATHER_ID = 3;

    NotificationManager manager;
    AlarmManager alarmManager;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        createNotificationChannels();
        int interval = DEFAULT_INTERVAL;
        boolean sendAllNotifications = DEFAULT_SEND_ALL;
        boolean sendNewsNotifications = DEFAULT_SEND_NEWS;
        boolean sendWeatherNotification = DEFAULT_SEND_WEATHER;
        try{
            sp = PreferenceManager.getDefaultSharedPreferences(this);

            interval = Integer.parseInt(sp.getString("preferences_notification_interval", String.valueOf(DEFAULT_INTERVAL)));
            Toast.makeText(this, "interval: " + interval, Toast.LENGTH_SHORT).show();
            sendAllNotifications = sp.getBoolean("preference_all_notifications", DEFAULT_SEND_ALL);
            sendNewsNotifications = sp.getBoolean("preference_news_notifications", DEFAULT_SEND_NEWS);
            sendWeatherNotification = sp.getBoolean("preference_weather_notifications", DEFAULT_SEND_WEATHER);


        }catch (NullPointerException npe){
            Toast.makeText(this, "no sp - interval: " + interval, Toast.LENGTH_SHORT).show();

        }
        finally {
            Log.d("NOTIFICATIONS", "all notif: "+ sendAllNotifications);

            if(sendAllNotifications)
                startNotifications(interval, "all");
            else if(sendNewsNotifications)
                startNotifications(interval, "news");
            else if(sendWeatherNotification)
                startNotifications(interval, "weather");
                //cancelNotifications();
        }

        //fragments instances
        NewsFragment newsFragment = NewsFragment.newInstance();
        WeatherFragment weatherFragment = WeatherFragment.newInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.news_container, newsFragment, NEWS_FRAGMENT_TAG);
        transaction.add(R.id.weather_container, weatherFragment, WEATHER_FRAGMENT_TAG);
        transaction.commit();

    }

    private void cancelNotifications() {
        Intent intent = new Intent(MainActivity.this, UpdateReceiver.class);
        intent.putExtra("sendNotif", false);
        PendingIntent broadcast = PendingIntent.getBroadcast(MainActivity.this, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, 0, broadcast);
        //manager.cancel(NOTIF_NEW_ARTICLE_ID);
        //manager.cancel(NOTIF_NEW_WEATHER_ID);
        //manager.cancel(NOTIF_LATEST_NEWS_ID);
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

            sp = PreferenceManager.getDefaultSharedPreferences(this);

            int interval = Integer.parseInt(sp.getString("preferences_notification_interval", String.valueOf(DEFAULT_INTERVAL)));
            Toast.makeText(this, "interval updated: " + interval, Toast.LENGTH_SHORT).show();

            boolean sendAllNotifications = sp.getBoolean("preference_all_notifications", DEFAULT_SEND_ALL);
            boolean sendNewsNotifications = sp.getBoolean("preference_news_notifications", DEFAULT_SEND_NEWS);
            boolean sendWeatherNotification = sp.getBoolean("preference_weather_notifications", DEFAULT_SEND_WEATHER);

            if(sendAllNotifications)
                startNotifications(interval, "all");
            else if(sendNewsNotifications)
                startNotifications(interval, "news");
            else if(sendWeatherNotification)
                startNotifications(interval, "weather");
            else
                cancelNotifications();
        }
    }

    private void startNotifications(int interval, String type) {

        //alarm manager


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, interval);

        //sp.edit().putString("type", type).commit();
        Intent intent = new Intent(MainActivity.this, UpdateReceiver.class);
        intent.putExtra("type", type);
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
