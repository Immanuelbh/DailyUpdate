package com.ijbh.dailyupdate.network;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.ijbh.dailyupdate.models.Article;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ArticlesJsonDeserializer implements JsonDeserializer {

    private static String TAG = ArticlesJsonDeserializer.class.getSimpleName();

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ArrayList<Article> articles = null;

        try{
            JsonObject jsonObject = json.getAsJsonObject();
            JsonArray articlesJsonArray = jsonObject.getAsJsonArray("articles");
            articles = new ArrayList<>(articlesJsonArray.size());
            for (int i = 0; i < articlesJsonArray.size(); i++) {
                Article dematerialized = context.deserialize(articlesJsonArray.get(i), Article.class);
                articles.add(dematerialized);
            }
        }catch (JsonParseException jpe){
            Log.e(TAG, String.format("Could not deserialize Article element: %s", json.toString()));
        }

        return articles;
    }
}
