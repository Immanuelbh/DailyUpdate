package com.ijbh.dailyupdate.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ijbh.dailyupdate.models.Article;
import com.ijbh.dailyupdate.network.Api;
import com.ijbh.dailyupdate.network.ApiUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleViewModel extends ViewModel {

    private MutableLiveData<List<Article>> articleList;

    public MutableLiveData<List<Article>> getArticleList() {
        if(articleList == null){
            articleList = new MutableLiveData<>();
            loadArticles();
        }
        return articleList;
    }

    private void loadArticles() {
        Api api = ApiUtil.getRetrofitApi();

        Call<ArrayList<Article>> call = api.getArticles("d0aa26443881471ab9a2f2f49298fc28");

        call.enqueue(new Callback<ArrayList<Article>>() {
            @Override
            public void onResponse(Call<ArrayList<Article>> call, Response<ArrayList<Article>> response) {
                articleList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Article>> call, Throwable t) {
                Log.d("Error", "Failed to create list");
            }
        });

    }
}
