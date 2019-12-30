package com.ijbh.dailyupdate.additional;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.ijbh.dailyupdate.R;
import com.ijbh.dailyupdate.ui.ArticleActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public class UpdateReceiver extends BroadcastReceiver {

    final String CHANNEL_NEWS_ID = "news_channel";
    final String CHANNEL_WEATHER_ID = "weather_channel";
    final CharSequence CHANNEL_NEWS_NAME = "News Channel";
    final CharSequence CHANNEL_WEATHER_NAME = "Weather Channel";
    final int NOTIF_NEW_ARTICLE_ID = 1;
    final int NOTIF_LATEST_NEWS_ID = 2;
    final int NOTIF_NEW_WEATHER_ID = 3;

    AlarmManager alarmManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        //notification
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                //(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        String channelId = null;
        if(Build.VERSION.SDK_INT >= 26){
            //
            //
            //
            manager.createNotificationChannel(new NotificationChannel(CHANNEL_NEWS_ID, CHANNEL_NEWS_NAME, NotificationManager.IMPORTANCE_HIGH));
            manager.createNotificationChannel(new NotificationChannel(CHANNEL_WEATHER_ID, CHANNEL_WEATHER_NAME, NotificationManager.IMPORTANCE_DEFAULT));
        }

        //builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_NEWS_ID);
        builder.setSmallIcon(R.drawable.ic_watch_later_black);

        RemoteViews remoteViews= new RemoteViews(context.getPackageName(), R.layout.article_notif_layout);



        Intent articleIntent = new Intent();
        //Intent articleIntent = new Intent(this, ArticleActivity.class);
        PendingIntent articlePendingIntent = PendingIntent.getActivity(context, 1, articleIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.article_notif_ll, articlePendingIntent);


        builder.setContent(remoteViews);
        manager.notify(NOTIF_NEW_ARTICLE_ID, builder.build());


        //notification from vid
        /*
        Intent notificationIntent = new Intent(context, ArticleActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ArticleActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"article_channel");

        Notification notification = builder.setContentTitle("Demo Notification")
                .setContentText("Sample text for testing..")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent).build();

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notification);
        */
    }
}
