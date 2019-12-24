package com.ijbh.dailyupdate.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ijbh.dailyupdate.R;
import com.ijbh.dailyupdate.models.Forecast;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private List<Forecast> forecasts;
    private ForecastListener listener;

    interface ForecastListener{
        void onForecastClicked(int position);
    }

    public void setListener(ForecastListener listener){
        this.listener = listener;
    }

    public ForecastAdapter(List<Forecast> forecasts){
        this.forecasts = forecasts;
    }

    public class ForecastViewHolder extends RecyclerView.ViewHolder{
        ImageView forecastIconIv;
        TextView forecastTitleTv;
        TextView forecastDegreeTv;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);

            forecastIconIv = itemView.findViewById(R.id.forecast_icon_iv);
            forecastTitleTv = itemView.findViewById(R.id.forecast_title_tv);
            forecastDegreeTv = itemView.findViewById(R.id.forecast_degrees_tv);
        }
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_cell, parent, false);
       ForecastViewHolder forecastViewHolder = new ForecastViewHolder(view);

       return forecastViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        Forecast forecast = forecasts.get(position);
        //holder.forecastIconIv. //set weather icon according to the temp
        holder.forecastTitleTv.setText(forecast.getForecastTitle());
        holder.forecastDegreeTv.setText(forecast.getForecastDegrees());
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }


}
