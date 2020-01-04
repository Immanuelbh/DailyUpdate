package com.ijbh.dailyupdate.additional;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.ijbh.dailyupdate.R;
import com.ijbh.dailyupdate.ui.ArticleActivity;
import com.ijbh.dailyupdate.ui.ForecastActivity;
import com.ijbh.dailyupdate.ui.MainActivity;

import java.util.Calendar;
import java.util.Random;

import static android.content.Context.NOTIFICATION_SERVICE;

public class UpdateReceiver extends BroadcastReceiver {

    private static final int NOTIF_AMOUNT = 3;
    final String CHANNEL_NEWS_ID = "news_channel";
    final String CHANNEL_WEATHER_ID = "weather_channel";

    final CharSequence CHANNEL_NEWS_NAME = "News Channel";
    final CharSequence CHANNEL_WEATHER_NAME = "Weather Channel";
    final int NOTIF_NEW_ARTICLE_ID = 1;
    final int NOTIF_LATEST_NEWS_ID = 2;
    final int NOTIF_NEW_WEATHER_ID = 3;

    AlarmManager alarmManager;
    NotificationManager manager;

    @Override
    public void onReceive(Context context, Intent intent) {

        //notification
        manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);


        //pending intents
        //pending intent for article notification
        Intent articleIntent = new Intent(context, ArticleActivity.class);
        PendingIntent articlePendingIntent = PendingIntent.getActivity(context, 1, articleIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        //pending intent for weather notification
        Intent forecastIntent = new Intent(context, ForecastActivity.class);
        PendingIntent forecastPendingIntent = PendingIntent.getActivity(context, 2, forecastIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        //pending intent for articles notification
        Intent articlesIntent = new Intent(context, MainActivity.class);
        PendingIntent articlesPendingIntent = PendingIntent.getActivity(context, 3, articlesIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        //remote views
        //remote view for article notification
        RemoteViews articleNotifRv= new RemoteViews(context.getPackageName(), R.layout.article_notif_layout_collapsed);
        articleNotifRv.setOnClickPendingIntent(R.id.article_notif_ll, articlePendingIntent);
        articleNotifRv.setTextViewText(R.id.article_title_notif, "New Article!");
        articleNotifRv.setImageViewResource(R.id.article_img_iv_notif, R.drawable.ic_watch_later_black);

        //remote view for weather notification
        RemoteViews forecastNotifRv= new RemoteViews(context.getPackageName(), R.layout.weather_notif_layout);
        forecastNotifRv.setOnClickPendingIntent(R.id.weather_notif_ll, forecastPendingIntent);
        articleNotifRv.setTextViewText(R.id.forecast_title_notif, "New Weather Update!");

        //remote view for articles notification


        //builders
        //builder for article notification
        NotificationCompat.Builder articleNotifBuilder = new NotificationCompat.Builder(context, CHANNEL_NEWS_ID);
        articleNotifBuilder.setSmallIcon(R.drawable.ic_watch_later_black);
        articleNotifBuilder.setContent(articleNotifRv);

        //builder for weather notification
        NotificationCompat.Builder forecastNotifBuilder = new NotificationCompat.Builder(context, CHANNEL_WEATHER_ID);
        forecastNotifBuilder.setSmallIcon(R.drawable.ic_watch_later_black);
        forecastNotifBuilder.setContent(forecastNotifRv);

        //builder for articles notification
        NotificationCompat.Builder articlesNotifBuilder = new NotificationCompat.Builder(context, CHANNEL_NEWS_ID);
        articlesNotifBuilder.setSmallIcon(R.drawable.ic_watch_later_black);
        articlesNotifBuilder.setContentTitle("New Articles, Come Check Them Out!");
        articlesNotifBuilder.setStyle(new NotificationCompat.DecoratedCustomViewStyle());
        //articlesNotifBuilder.setContent(articlesNotifRv);


        //notifies
        NotificationCompat.Builder[] notifications = new NotificationCompat.Builder[NOTIF_AMOUNT];
        notifications[0] = articleNotifBuilder;
        notifications[1] = forecastNotifBuilder;
        notifications[2] = articlesNotifBuilder;

        //manager.notify(NOTIF_NEW_WEATHER_ID, forecastNotifBuilder.build());
        fireNotification(notifications);

        //manager.cancel(NOTIF_NEW_ARTICLE_ID);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        int interval = Integer.parseInt(sp.getString("preferences_notification_interval", "10"));
        Toast.makeText(context, "interval: " + interval, Toast.LENGTH_SHORT).show();


        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, interval);

        Intent updateIntent = new Intent(context, UpdateReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);

    }

    private void fireNotification(NotificationCompat.Builder[] notifications) {

        Random rand = new Random();
        int num = rand.nextInt(notifications.length);
        Log.d("UpdateReceiver", "Attempting to fire notification number: " + num);
        switch (num){
            case 0: manager.notify(NOTIF_NEW_ARTICLE_ID, notifications[num].build());
                    break;
            case 1: manager.notify(NOTIF_NEW_WEATHER_ID, notifications[num].build());
                    break;
            case 2: manager.notify(NOTIF_LATEST_NEWS_ID, notifications[num].build());
                    break;

        }

    }
}
