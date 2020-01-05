package com.ijbh.dailyupdate.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ijbh.dailyupdate.R;
import com.ijbh.dailyupdate.models.Forecast;
import com.ijbh.dailyupdate.viewmodel.ForecastViewModel;

import java.util.Locale;

public class ForecastActivity extends Activity {

    private static final String IMG_ICON_URL = "https://www.weatherbit.io/static/img/icons/";

    private Forecast forecast;

    ImageView forecastImgIv;
    TextView forecastCityTv;
    //TextView forecastCountryCodeTv;
    TextView forecastDescTv;
    TextView forecastAvgTempTv;
    TextView forecastMaxTempTv;
    TextView forecastMinTempTv;
    TextView forecastWndDirTv;
    TextView forecastWndSpdTv;
    TextView forecastPopTv;
    TextView forecastPrecipTv;
    TextView forecastDateTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        forecastImgIv = findViewById(R.id.forecast_img_layout_tv);
        forecastCityTv = findViewById(R.id.forecast_city_layout_tv);
        //forecastCountryCodeTv = findViewById(R.id.);
        forecastDescTv = findViewById(R.id.forecast_desc_tv);
        forecastAvgTempTv = findViewById(R.id.forecast_avg_temp_tv);
        forecastMaxTempTv = findViewById(R.id.forecast_max_temp_tv);
        forecastMinTempTv = findViewById(R.id.forecast_min_temp_tv);
        forecastWndDirTv = findViewById(R.id.forecast_wnd_dir_tv);
        forecastWndSpdTv = findViewById(R.id.forecast_wnd_spd_tv);
        forecastPopTv = findViewById(R.id.forecast_pop_tv);
        forecastPrecipTv = findViewById(R.id.forecast_precip_tv);
        forecastDateTv = findViewById(R.id.forecast_day_layout_tv);


        forecast = ForecastViewModel.getForecast(getIntent().getIntExtra("current_forecast", 0));

        if (forecast != null) {

            String lang = Locale.getDefault().getCountry();


            Log.d("Weather ICON", IMG_ICON_URL + forecast.getForecastIcon() + ".png");
            Glide.with(this)
                    .load(IMG_ICON_URL + forecast.getForecastIcon() + ".png")
                    .override(360,360)
                    .into(forecastImgIv);
            forecastCityTv.setText(forecast.getForecastCity() + ", " + forecast.getCountryCode());
            forecastDateTv.setText(forecast.getDayOfTheWeek());
            forecastAvgTempTv.setText(forecast.getAvgTemp() + "° C");
            forecastMaxTempTv.setText(forecast.getForecastMaxDegrees() + "°");
            forecastMinTempTv.setText(forecast.getForecastMinDegrees()+ "°");
            forecastWndDirTv.setText(forecast.getWindDirection());
            forecastWndSpdTv.setText(forecast.getWindSpeed() + "m/s");
            forecastPopTv.setText(forecast.getPropOfPrecip() + "%");
            forecastPrecipTv.setText(forecast.getPrecip() + "mm");

            if(lang.equals("us"))
                forecastDescTv.setText(forecast.getWeatherDesc());
            else{
                forecastDescTv.setVisibility(View.GONE);
            }
        }
        else{
            Toast.makeText(this, "The article is null", Toast.LENGTH_SHORT).show();
        }
    }
}
