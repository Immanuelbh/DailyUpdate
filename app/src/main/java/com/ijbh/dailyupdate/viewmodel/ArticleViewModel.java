package com.ijbh.dailyupdate.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ijbh.dailyupdate.R;
import com.ijbh.dailyupdate.models.Article;
import com.ijbh.dailyupdate.network.Api;
import com.ijbh.dailyupdate.network.ApiUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleViewModel extends ViewModel {

    private static final String TAG = "CHECK_URL";
    private static MutableLiveData<List<Article>> articleList;

    public MutableLiveData<List<Article>> getArticleList() {
        if(articleList == null){
            articleList = new MutableLiveData<>();
            loadArticles();
        }
        return articleList;
    }

    private void loadArticles() {
        Api api = ApiUtil.getRetrofitApi("news");

        String lang = Locale.getDefault().getCountry();
        Call<ArrayList<Article>> call = api.getArticles(lang,"d0aa26443881471ab9a2f2f49298fc28");
        Log.d(TAG, "onResponse: ConfigurationListener::"+call.request().url());

        call.enqueue(new Callback<ArrayList<Article>>() {
            @Override
            public void onResponse(Call<ArrayList<Article>> call, Response<ArrayList<Article>> response) {
                articleList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Article>> call, Throwable t) {
                Log.d("Error", "Failed to create list");
                t.printStackTrace();
            }
        });

    }

    public static Article getArticle(int position){
        try{
            return articleList.getValue().get(position);
        }catch (IndexOutOfBoundsException e){
            Log.d("Get Article", "The position: " + position + " is out of bounds");
            return null;
        }
    }

}
