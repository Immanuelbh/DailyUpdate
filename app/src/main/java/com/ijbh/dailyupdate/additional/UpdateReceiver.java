package com.ijbh.dailyupdate.additional;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.ijbh.dailyupdate.R;
import com.ijbh.dailyupdate.models.Article;
import com.ijbh.dailyupdate.models.Forecast;
import com.ijbh.dailyupdate.ui.ArticleActivity;
import com.ijbh.dailyupdate.ui.ForecastActivity;
import com.ijbh.dailyupdate.ui.MainActivity;
import com.ijbh.dailyupdate.viewmodel.ArticleViewModel;
import com.ijbh.dailyupdate.viewmodel.ForecastViewModel;

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
        PendingIntent articlePendingIntent = PendingIntent.getActivity(context, 1, articleIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //pending intent for weather notification
        Intent forecastIntent = new Intent(context, ForecastActivity.class);
        PendingIntent forecastPendingIntent = PendingIntent.getActivity(context, 2, forecastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //pending intent for articles notification
        Intent articlesIntent = new Intent(context, MainActivity.class);
        PendingIntent articlesPendingIntent = PendingIntent.getActivity(context, 3, articlesIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //remote views
        //remote view for article notification

        RemoteViews articleNotifRv= new RemoteViews(context.getPackageName(), R.layout.article_notif_layout_collapsed);
        Article article = ArticleViewModel.getArticle(0);
        articleNotifRv.setTextViewText(R.id.article_title_notif, article.getTitle());
        articleNotifRv.setImageViewResource(R.id.article_img_iv_notif,R.drawable.newspaper);


        //remote view for weather notification
        RemoteViews forecastNotifRv= new RemoteViews(context.getPackageName(), R.layout.weather_notif_layout);
        Forecast forecast = ForecastViewModel.getForecast(0);
        forecastNotifRv.setTextViewText(R.id.forecast_title_notif, context.getResources().getString(R.string.expected_temp) + forecast.getAvgTemp() + "° C");
        forecastNotifRv.setTextViewText(R.id.weather_notif_second_title, context.getResources().getString(R.string.latest_forecast));
        forecastNotifRv.setImageViewResource(R.id.weather_img_notif, R.drawable.sun);

        //remote view for articles notification
        RemoteViews articlesNotifRv = new RemoteViews(context.getPackageName(), R.layout.articles_notif_layout);
        articlesNotifRv.setTextViewText(R.id.articles_title_notif, context.getResources().getString(R.string.new_info));
        articlesNotifRv.setTextViewText(R.id.articles_notif_second_title, context.getResources().getString(R.string.read_more_notif));
        articlesNotifRv.setImageViewResource(R.id.articles_img_iv_notif,R.drawable.newspaper2);


        //builders
        //builder for article notification

        NotificationCompat.Builder articleNotifBuilder = new NotificationCompat.Builder(context, CHANNEL_NEWS_ID);
        articleNotifBuilder.setSmallIcon(R.mipmap.ic_dailyupdate_foreground)
                            .setContentIntent(articlePendingIntent)
                            .setContent(articleNotifRv)
                            .setAutoCancel(true);

        //builder for weather notification
        NotificationCompat.Builder forecastNotifBuilder = new NotificationCompat.Builder(context, CHANNEL_WEATHER_ID);
        forecastNotifBuilder.setSmallIcon(R.drawable.ic_watch_later_black)
                            .setContentIntent(forecastPendingIntent)
                            .setContent(forecastNotifRv)
                            .setAutoCancel(true);

        //builder for articles notification
        NotificationCompat.Builder articlesNotifBuilder = new NotificationCompat.Builder(context, CHANNEL_NEWS_ID);
        articlesNotifBuilder.setSmallIcon(R.drawable.ic_watch_later_black)
                            .setContentIntent(articlesPendingIntent)
                            .setContent(articlesNotifRv)
                            .setAutoCancel(true);


        NotificationCompat.Builder[] notifications = null;// = new NotificationCompat.Builder[NOTIF_AMOUNT];

        String notifType = intent.getStringExtra("type");

        if(notifType == null){
            cancelNotifications("all");
        }
        else{
            Log.d("Notif Type:", notifType);
            if(notifType.equals("all")){
                notifications = new NotificationCompat.Builder[NOTIF_AMOUNT];
                notifications[0] = articleNotifBuilder;
                notifications[1] = forecastNotifBuilder;
                notifications[2] = articlesNotifBuilder;
            }
            else if (notifType.equals("news")){
                notifications = new NotificationCompat.Builder[NOTIF_AMOUNT-1];
                //notifications[0] = articleNotifBuilder;
                notifications[1] = articlesNotifBuilder;
            }
            else if (notifType.equals("weather")){
                notifications = new NotificationCompat.Builder[NOTIF_AMOUNT-2];
                notifications[0] = forecastNotifBuilder;

            }


        }

        //notifies


        if(notifications != null)
            fireNotification(notifications);


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        int interval = Integer.parseInt(sp.getString("preferences_notification_interval", "10"));
        Toast.makeText(context, "interval: " + interval, Toast.LENGTH_SHORT).show();



        if(notifType != null){

            alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, interval);

            Intent updateIntent = new Intent(context, UpdateReceiver.class);
            updateIntent.putExtra("type", notifType);
            PendingIntent broadcast = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);
        }

    }

    private void cancelNotifications(String toCancel) {
        if (toCancel.equals("all")){
            try {
                manager.cancel(NOTIF_NEW_ARTICLE_ID);
            }catch (NullPointerException npe1){}
            try {
                manager.cancel(NOTIF_NEW_WEATHER_ID);
            }catch (NullPointerException npe1){}
            try {
                manager.cancel(NOTIF_LATEST_NEWS_ID);
            }catch (NullPointerException npe1){}
        }
        else if (toCancel.equals("news")){
            try {
                manager.cancel(NOTIF_NEW_ARTICLE_ID);
            }catch (NullPointerException npe1){}
            try {
                manager.cancel(NOTIF_LATEST_NEWS_ID);
            }catch (NullPointerException npe1){}
        }
        else if (toCancel.equals("weather")){
            try {
                manager.cancel(NOTIF_NEW_WEATHER_ID);
            }catch (NullPointerException npe1){}
        }


    }

    private void fireNotification(NotificationCompat.Builder[] notifications) {

        Random rand = new Random();
        int num = rand.nextInt(notifications.length);
        int type = notifications.length;
        Log.d("UpdateReceiver", "length: " + notifications.length);
        Log.d("UpdateReceiver", "Attempting to fire notification number: " + num);

        if(type == 3){
            switch (num){
                case 0: manager.notify(NOTIF_NEW_ARTICLE_ID, notifications[num].build());
                    break;
                case 1: manager.notify(NOTIF_NEW_WEATHER_ID, notifications[num].build());
                    break;
                case 2: manager.notify(NOTIF_LATEST_NEWS_ID, notifications[num].build());
                    break;

            }

        }
        else if (type == 2){
            cancelNotifications("weather");
            switch (num){
                case 0: manager.notify(NOTIF_NEW_ARTICLE_ID, notifications[num].build());
                    break;
                case 1: manager.notify(NOTIF_LATEST_NEWS_ID, notifications[num].build());
                    break;

            }
        }
        else if (type == 1){
            cancelNotifications("news");
            manager.notify(NOTIF_NEW_WEATHER_ID, notifications[num].build());

        }

    }
}
