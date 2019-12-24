package com.ijbh.dailyupdate.network;

import com.ijbh.dailyupdate.models.Article;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api{
    @GET(".")
    Call<ArrayList<Article>> getArticles(@Query("api_key") String apiKey);

}
