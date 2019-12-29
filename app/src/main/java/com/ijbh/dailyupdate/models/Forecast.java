package com.ijbh.dailyupdate.models;


import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Forecast {
    @SerializedName(value = "weather.icon")
    private String forecastIcon;
    //@SerializedName(value = "")
    private String forecastCity;
    @SerializedName(value = "temp")
    private String forecastDegrees;
    @SerializedName(value = "max_temp")
    private String forecastMaxDegrees;
    @SerializedName(value = "min_temp")
    private String forecastMinDegrees;
    @SerializedName(value = "valid_date")
    private String date;


    public Forecast(String forecastTitle, String forecastDegrees) {
        this.forecastCity = forecastTitle;
        this.forecastDegrees = forecastDegrees;
        //calcForecastIcon(forecastDegrees);
    }

    public Forecast(String forecastIcon, String forecastTitle, String forecastDegrees) {
        this.forecastIcon = forecastIcon;
        this.forecastCity = forecastTitle;
        this.forecastDegrees = forecastDegrees;
    }

    public String getForecastIcon() {
        return forecastIcon;
    }

    public void setForecastIcon(String forecastIcon) {
        this.forecastIcon = forecastIcon;
    }

    public String getForecastTitle() {
        return forecastCity;
    }

    public void setForecastTitle(String forecastTitle) {
        this.forecastCity = forecastTitle;
    }

    public String getForecastDegrees() {
        return forecastDegrees;
    }

    public void setForecastDegrees(String forecastDegrees) {
        this.forecastDegrees = forecastDegrees;
    }

    public String getForecastMaxDegrees() {
        return forecastMaxDegrees;
    }

    public void setForecastMaxDegrees(String forecastMaxDegrees) {
        this.forecastMaxDegrees = forecastMaxDegrees;
    }

    public String getForecastMinDegrees() {
        return forecastMinDegrees;
    }

    public void setForecastMinDegrees(String forecastMinDegrees) {
        this.forecastMinDegrees = forecastMinDegrees;
    }

    public String getDayOfTheWeek(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String day = null;
        try {
            Date currentDate = sdf.parse(date);
            DateFormat dayFormat = new SimpleDateFormat("EEEE");
            day = dayFormat.format(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return day;

    }
}
