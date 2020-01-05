package com.ijbh.dailyupdate.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ijbh.dailyupdate.R;
import com.ijbh.dailyupdate.models.Forecast;
import com.ijbh.dailyupdate.ui.ArticleActivity;
import com.ijbh.dailyupdate.ui.ForecastActivity;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private static final String BASE_ICON_URL = "https://www.weatherbit.io/static/img/icons/";
    private Context fCtx;
    private List<Forecast> forecasts;
    private ForecastListener listener;
    private String latLon = null;

    public interface ForecastListener{
        void onForecastClicked(int position);
    }

    public void setForecastListener(ForecastListener listener){
        this.listener = listener;
    }

    public ForecastAdapter(Context fCtx, List<Forecast> forecasts) {
        this.fCtx = fCtx;
        this.forecasts = forecasts;
    }

    public ForecastAdapter(List<Forecast> forecasts){
        this.forecasts = forecasts;
    }

    public class ForecastViewHolder extends RecyclerView.ViewHolder{
        ImageView forecastIconIv;
        TextView forecastTitleTv;
        TextView forecastMaxDegreeTv;
        TextView forecastMinDegreeTv;
        TextView forecastDayTv;
        RelativeLayout containerRl;


        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);

            forecastIconIv = itemView.findViewById(R.id.forecast_icon_iv);
            forecastTitleTv = itemView.findViewById(R.id.forecast_title_tv);
            forecastMaxDegreeTv = itemView.findViewById(R.id.forecast_max_degrees_tv);
            forecastMinDegreeTv = itemView.findViewById(R.id.forecast_min_degrees_tv);
            forecastDayTv = itemView.findViewById(R.id.day_of_the_week_tv);
            containerRl = itemView.findViewById(R.id.container);
        }
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_cell, parent, false);
       ForecastViewHolder forecastViewHolder = new ForecastViewHolder(view);

        //working
        final ForecastAdapter.ForecastViewHolder fvh = new ForecastAdapter.ForecastViewHolder(view);
        fvh.containerRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(fCtx, "Forecast num: " + String.valueOf(fvh.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(parent.getContext(), ForecastActivity.class);
                intent.putExtra("current_forecast", fvh.getAdapterPosition());
                fCtx.startActivity(intent);
            }
        });

        return fvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        Forecast forecast = forecasts.get(position);
        String iconUrl = BASE_ICON_URL + forecast.getForecastIcon() + ".png";
        Log.d("ICON_URL", iconUrl);
        Glide.with(fCtx)
                .load(iconUrl)
                .apply(new RequestOptions().override(200,200))
                .into(holder.forecastIconIv);//holder.forecastIconIv. //set weather icon according to the temp
        holder.forecastTitleTv.setText(forecast.getForecastTitle());
        holder.forecastMaxDegreeTv.setText(forecast.getForecastMaxDegrees()+"°");
        holder.forecastMinDegreeTv.setText(forecast.getForecastMinDegrees()+"°");
        holder.forecastDayTv.setText(forecast.getDayOfTheWeek());
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }


}
