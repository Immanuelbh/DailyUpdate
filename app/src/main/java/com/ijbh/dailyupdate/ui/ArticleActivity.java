package com.ijbh.dailyupdate.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ijbh.dailyupdate.R;


public class ArticleActivity extends AppCompatActivity{

    final int NOTIF_NEW_ARTICLE_ID = 1;

    NotificationManagerCompat manager;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);



        Button stopBtn = findViewById(R.id.stop_btn);

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
