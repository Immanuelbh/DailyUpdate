package com.ijbh.dailyupdate.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ijbh.dailyupdate.R;
import com.ijbh.dailyupdate.models.Article;
import com.ijbh.dailyupdate.viewmodel.ArticleViewModel;


public class ArticleActivity extends Activity {

    private Article article;

    ImageView articleImgIv;
    TextView articleTitleTv;
    TextView articleAuthorTv;
    TextView articleDateTv;
    TextView articleSourceTv;
    TextView articleContentTv;
    
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        articleImgIv = findViewById(R.id.article_layout_img_iv);
        articleTitleTv = findViewById(R.id.article_title_layout);
        articleAuthorTv = findViewById(R.id.article_author_tv);
        articleDateTv = findViewById(R.id.article_date_tv);
        articleSourceTv = findViewById(R.id.article_source_tv);
        articleContentTv = findViewById(R.id.article_content_tv);


        article = ArticleViewModel.getArticle(getIntent().getIntExtra("current_article", 0));
        
        if(article != null){
            Glide.with(this).load(article.getImageUrl()).into(articleImgIv);
            articleTitleTv.setText(article.getTitle());
            articleAuthorTv.setText(article.getAuthor());
            articleDateTv.setText(article.getPublished());
            articleSourceTv.setText(article.getSource());
            articleContentTv.setText(article.getContent());


        }
        else{
            Toast.makeText(this, "The article is null", Toast.LENGTH_SHORT).show();
        }

    }

}
