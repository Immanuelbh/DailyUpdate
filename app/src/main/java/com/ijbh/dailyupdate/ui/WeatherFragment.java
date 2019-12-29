package com.ijbh.dailyupdate.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ijbh.dailyupdate.R;
import com.ijbh.dailyupdate.adapters.ArticleAdapter;
import com.ijbh.dailyupdate.models.Forecast;
import com.ijbh.dailyupdate.adapters.ForecastAdapter;
import com.ijbh.dailyupdate.viewmodel.ForecastViewModel;

import java.util.ArrayList;
import java.util.List;

public class WeatherFragment extends Fragment {
    final String TAG = "WeatherFragment";

    ForecastAdapter adapter;
    //interface
    public interface OnWeatherFragmentListener{

    }

    OnWeatherFragmentListener callback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            callback = (OnWeatherFragmentListener) context;
        }catch (ClassCastException cce){
            throw new ClassCastException("The activity must implement OnForecastFragmentListener interface");
        }
    }

    public static WeatherFragment newInstance(){
        WeatherFragment weatherFragment = new WeatherFragment();

        return weatherFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.forecast_fragment, container, false);

        final RecyclerView recyclerView = rootView.findViewById(R.id.forecast_recycler);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.HORIZONTAL, false));

        ForecastViewModel model = ViewModelProviders.of(this).get(ForecastViewModel.class);

        model.getForecastList().observe(this, new Observer<List<Forecast>>() {
            @Override
            public void onChanged(List<Forecast> forecasts) {

                adapter = new ForecastAdapter(WeatherFragment.this.getContext(), forecasts);
                recyclerView.setAdapter(adapter);
            }
        });
        /*
        List<Forecast> forecastList = new ArrayList<>();
        forecastList.add(new Forecast("Tel Aviv", "22"));
        forecastList.add(new Forecast("Haifa" , "10"));

        ForecastAdapter forecastAdapter = new ForecastAdapter(forecastList);

        recyclerView.setAdapter(forecastAdapter);
        */
        return rootView;
    }
}
