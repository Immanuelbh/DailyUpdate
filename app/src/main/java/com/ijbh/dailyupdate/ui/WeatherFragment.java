package com.ijbh.dailyupdate.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ijbh.dailyupdate.R;
import com.ijbh.dailyupdate.models.Forecast;
import com.ijbh.dailyupdate.adapters.ForecastAdapter;

import java.util.ArrayList;
import java.util.List;

public class WeatherFragment extends Fragment {
    final String TAG = "WeatherFragment";

    //interface


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //setting the callback
    }

    public static WeatherFragment newInstance(){
        WeatherFragment weatherFragment = new WeatherFragment();

        return weatherFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.forecast_fragment, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.forecast_recycler);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.HORIZONTAL, false));

        List<Forecast> forecastList = new ArrayList<>();
        forecastList.add(new Forecast("Tel Aviv", "22"));
        forecastList.add(new Forecast("Haifa" , "10"));

        ForecastAdapter forecastAdapter = new ForecastAdapter(forecastList);

        recyclerView.setAdapter(forecastAdapter);

        return rootView;
    }
}
