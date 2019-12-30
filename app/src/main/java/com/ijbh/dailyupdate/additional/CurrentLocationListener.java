package com.ijbh.dailyupdate.additional;

import android.content.Context;
import android.location.Location;

import androidx.lifecycle.LiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class CurrentLocationListener extends LiveData<Location> {


    private static final long INTERVAL_TIME = 600000; // = 10minutes
    private static final long FASTEST_INTERVAL_TIME = 60000; // = 1minute

    private static CurrentLocationListener instance;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;

    public static CurrentLocationListener getInstance(Context context){
        if(instance == null)
            instance = new CurrentLocationListener(context);
        return instance;
    }

    private CurrentLocationListener(Context context){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null)
                    setValue(location);
            }
        });
        createLocationRequest();
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL_TIME);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL_TIME);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected void onActive() {
        super.onActive();
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }

    LocationCallback mLocationCallback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for(Location location : locationResult.getLocations()){
                if(location != null)
                    setValue(location);
            }
        }
    };

    @Override
    protected void onInactive() {
        super.onInactive();
        if(mLocationCallback != null)
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }
}
