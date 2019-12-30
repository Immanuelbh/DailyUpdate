package com.ijbh.dailyupdate.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RemoteViews;

import com.ijbh.dailyupdate.R;
import com.ijbh.dailyupdate.additional.UpdateReceiver;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NewsFragment.OnNewsFragmentListener, WeatherFragment.OnWeatherFragmentListener{

    final String NEWS_FRAGMENT_TAG = "news_fragment";
    final String WEATHER_FRAGMENT_TAG = "weather_fragment";
    final String CHANNEL_NEWS_ID = "news_channel";
    final String CHANNEL_WEATHER_ID = "weather_channel";
    final CharSequence CHANNEL_NEWS_NAME = "News Channel";
    final CharSequence CHANNEL_WEATHER_NAME = "Weather Channel";
    final int NOTIF_NEW_ARTICLE_ID = 1;
    final int NOTIF_LATEST_NEWS_ID = 2;
    final int NOTIF_NEW_WEATHER_ID = 3;

    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 5);

        Intent intent = new Intent(MainActivity.this, UpdateReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(MainActivity.this, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);



        ////////
        //notifications
        /*
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        String channelId = null;
        if(Build.VERSION.SDK_INT >= 26){
            //
            //
            //
            manager.createNotificationChannel(new NotificationChannel(CHANNEL_NEWS_ID, CHANNEL_NEWS_NAME, NotificationManager.IMPORTANCE_HIGH));
            manager.createNotificationChannel(new NotificationChannel(CHANNEL_WEATHER_ID, CHANNEL_WEATHER_NAME, NotificationManager.IMPORTANCE_DEFAULT));
        }

        //builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_NEWS_ID);
        builder.setSmallIcon(R.drawable.ic_watch_later_black);

        RemoteViews remoteViews= new RemoteViews(getPackageName(), R.layout.article_notif_layout);



        Intent articleIntent = new Intent();
        //Intent articleIntent = new Intent(this, ArticleActivity.class);
        PendingIntent articlePendingIntent = PendingIntent.getActivity(this, 1, articleIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.article_notif_ll, articlePendingIntent);


        builder.setContent(remoteViews);
        manager.notify(NOTIF_NEW_ARTICLE_ID, builder.build());

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 30 * 1000, intent);
        */


        //fragments instances
        NewsFragment newsFragment = NewsFragment.newInstance();
        WeatherFragment weatherFragment = WeatherFragment.newInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.news_container, newsFragment, NEWS_FRAGMENT_TAG);
        transaction.add(R.id.weather_container, weatherFragment, WEATHER_FRAGMENT_TAG);
        //transaction.addToBackStack(null);
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
