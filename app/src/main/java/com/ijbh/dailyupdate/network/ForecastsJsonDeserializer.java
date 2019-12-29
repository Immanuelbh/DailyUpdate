package com.ijbh.dailyupdate.network;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.ijbh.dailyupdate.models.Article;
import com.ijbh.dailyupdate.models.Forecast;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ForecastsJsonDeserializer implements JsonDeserializer {
    private static String TAG = ArticlesJsonDeserializer.class.getSimpleName();

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ArrayList<Forecast> forecasts = null;

        try{
            JsonObject jsonObject = json.getAsJsonObject();
            String city = jsonObject.get("city_name").getAsString();
            JsonArray forecastsJsonArray = jsonObject.getAsJsonArray("data");
            forecasts = new ArrayList<>(forecastsJsonArray.size());
            for (int i = 0; i < forecastsJsonArray.size(); i++) {
                JsonObject forecastObject = forecastsJsonArray.get(i).getAsJsonObject();
                JsonObject weatherObject = forecastObject.get("weather").getAsJsonObject();
                //TODO get weather icon from JSON
                Forecast dematerialized = context.deserialize(forecastObject, Forecast.class);
                forecasts.add(dematerialized);
                forecasts.get(i).setForecastTitle(city);
                forecasts.get(i).setForecastIcon(weatherObject.get("icon").getAsString());
            }
        }catch (JsonParseException jpe){
            Log.e(TAG, String.format("Could not deserialize Forecast element: %s", json.toString()));
        }

        return forecasts;
    }
}
