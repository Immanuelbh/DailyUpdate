package com.ijbh.dailyupdate.forecasts;

import android.net.Uri;

public class Forecast {
    private Uri forecastIconUri;
    private String forecastTitle;
    private String forecastDegrees;

    public Forecast(String forecastTitle, String forecastDegrees) {
        this.forecastTitle = forecastTitle;
        this.forecastDegrees = forecastDegrees;
    }

    public Forecast(Uri forecastIconUri, String forecastTitle, String forecastDegrees) {
        this.forecastIconUri = forecastIconUri;
        this.forecastTitle = forecastTitle;
        this.forecastDegrees = forecastDegrees;
    }

    public Uri getForecastIconUri() {
        return forecastIconUri;
    }

    public void setForecastIconUri(Uri forecastIconUri) {
        this.forecastIconUri = forecastIconUri;
    }

    public String getForecastTitle() {
        return forecastTitle;
    }

    public void setForecastTitle(String forecastTitle) {
        this.forecastTitle = forecastTitle;
    }

    public String getForecastDegrees() {
        return forecastDegrees;
    }

    public void setForecastDegrees(String forecastDegrees) {
        this.forecastDegrees = forecastDegrees;
    }
}
