package com.ijbh.dailyupdate.network;

import com.ijbh.dailyupdate.models.Article;
import com.ijbh.dailyupdate.models.Forecast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api{
    //@GET("/top-headlines")
    //Call<ArrayList<Article>> getArticles(@Query("country=us&api_key") String apiKey);
    @GET(".")
    Call<ArrayList<Article>> getArticles(@Query("country") String country,
                                         @Query("apiKey") String apiKey);

    @GET(".")
    Call<ArrayList<Forecast>> getForecasts(@Query("lon") String lon,
                                           @Query("lat") String lat,
                                           @Query("days") String days,
                                           @Query("key") String key);

}
