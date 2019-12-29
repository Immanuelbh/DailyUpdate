package com.ijbh.dailyupdate.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ijbh.dailyupdate.models.Article;
import com.ijbh.dailyupdate.models.Forecast;
import com.ijbh.dailyupdate.network.Api;
import com.ijbh.dailyupdate.network.ApiUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastViewModel extends ViewModel {

    private static final String TAG = "CHECK_URL";
    private MutableLiveData<List<Forecast>> forecastList;

    public MutableLiveData<List<Forecast>> getForecastList() {
        if(forecastList == null){
            forecastList = new MutableLiveData<>();
            loadArticles();
        }
        return forecastList;
    }

    private void loadArticles() {
        Api api = ApiUtil.getRetrofitApi("weather");

        //TODO add dynamic lat and lon values
        Call<ArrayList<Forecast>> call = api.getForecasts("34.855499", "32.109333", "10", "360f7db3702548598ed50ec3d97b4015");
        Log.d(TAG, "onResponse: ConfigurationListener::"+call.request().url()); //for debugging

        call.enqueue(new Callback<ArrayList<Forecast>>() {
            @Override
            public void onResponse(Call<ArrayList<Forecast>> call, Response<ArrayList<Forecast>> response) {
                forecastList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Forecast>> call, Throwable t) {
                Log.d("Error", "Failed to create list");
                t.printStackTrace();
            }
        });

    }
}
