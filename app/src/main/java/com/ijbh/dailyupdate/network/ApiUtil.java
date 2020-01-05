package com.ijbh.dailyupdate.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ijbh.dailyupdate.models.Article;
import com.ijbh.dailyupdate.models.Forecast;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtil {

    static String NEWS_BASE_URL = "https://newsapi.org/v2/top-headlines/";
    static String WEATHER_BASE_URL = "https://api.weatherbit.io/v2.0/forecast/daily/";

    static final Type ARTICLE_ARRAY_LIST_CLASS_TYPE = (new ArrayList<Article>()).getClass();
    static final Type WEATHER_ARRAY_LIST_CLASS_TYPE = (new ArrayList<Forecast>()).getClass();

    public static Api getRetrofitApi(String apiType){
        Gson gson = null;
        Retrofit retrofit = null;

        if(apiType.equals("news")){
            gson = new GsonBuilder()
                    .registerTypeAdapter(ARTICLE_ARRAY_LIST_CLASS_TYPE, new ArticlesJsonDeserializer())
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(NEWS_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        else if(apiType.equals("weather")){
            gson = new GsonBuilder()
                    .registerTypeAdapter(WEATHER_ARRAY_LIST_CLASS_TYPE, new ForecastsJsonDeserializer())
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(WEATHER_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }


        Api api = retrofit.create(Api.class);

        return api;
    }
}
