package com.ijbh.dailyupdate.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ijbh.dailyupdate.R;
import com.ijbh.dailyupdate.adapters.ArticleAdapter;
import com.ijbh.dailyupdate.additional.CurrentLocationListener;
import com.ijbh.dailyupdate.models.Forecast;
import com.ijbh.dailyupdate.adapters.ForecastAdapter;
import com.ijbh.dailyupdate.viewmodel.ForecastViewModel;

import java.util.ArrayList;
import java.util.List;

public class WeatherFragment extends Fragment {
    final private String TAG = "WeatherFragment";
    final private int LOC_PERMISSION = 1;

    ForecastAdapter adapter;
    private double lat;
    private double lon;

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

        //setting current location
        if(ActivityCompat.checkSelfPermission(WeatherFragment.this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(WeatherFragment.this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, LOC_PERMISSION);

        }
        else{
            getLocationUpdates();
        }


        //setting up the model
        ForecastViewModel model = ViewModelProviders.of(this).get(ForecastViewModel.class);

        model.getForecastList(lat,lon).observe(this, new Observer<List<Forecast>>() {
            @Override
            public void onChanged(List<Forecast> forecasts) {

                adapter = new ForecastAdapter(WeatherFragment.this.getContext(), forecasts);
                recyclerView.setAdapter(adapter);
            }
        });



        return rootView;
    }

    private void getLocationUpdates() {
        CurrentLocationListener.getInstance(getContext()).observe(this, new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                lat = location.getLatitude();
                lon = location.getLongitude();
                Log.d(TAG, "lat = " + lat + " | lon = " + lon);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == LOC_PERMISSION){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                getLocationUpdates();
            }
        }
    }

}
