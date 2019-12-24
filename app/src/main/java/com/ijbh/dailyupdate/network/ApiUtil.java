package com.ijbh.dailyupdate.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ijbh.dailyupdate.models.Article;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtil {

    static String NEWS_BASE_URL = "https://newsapi.org/v2/";
    //static String TOP_HEADLINES_URL = ""

    static final Type ARTICLE_ARRAY_LIST_CLASS_TYPE = (new ArrayList<Article>()).getClass();

    public static Api getRetrofitApi(){

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ARTICLE_ARRAY_LIST_CLASS_TYPE, new ArticlesJsonDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NEWS_BASE_URL) //TODO figure out how to read the url correctly - adding queries to the url and so on
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Api api = retrofit.create(Api.class);

        return api;
    }
}
