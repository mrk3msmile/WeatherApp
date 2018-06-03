package com.example.vudou.weatherapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vudou on 10/11/2017.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.RecyclerViewHolder> {
    private Context context;
    private ArrayList<Weather> listWeather;

    public WeatherAdapter(Context context, ArrayList<Weather> listWeather) {
        this.context = context;
        this.listWeather = listWeather;
    }

    public WeatherAdapter() {
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.layout_weather, parent, false);
        return new RecyclerViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.txtDate.setText(listWeather.get(position).getDate());
        holder.txtTemp.setText(listWeather.get(position).getTemperature());
        holder.imgWeather.setImageResource(listWeather.get(position).getPicRes());
        holder.txtDesc.setText(listWeather.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return listWeather.size();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Weather> getListWeather() {
        return listWeather;
    }

    public void setListWeather(ArrayList<Weather> listWeather) {
        this.listWeather = listWeather;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDate, txtTemp, txtDesc;
        private ImageView imgWeather;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtDate = (TextView) itemView.findViewById(R.id.w_date);
            txtTemp = (TextView) itemView.findViewById(R.id.w_temp);
            txtDesc = (TextView) itemView.findViewById(R.id.w_desc);
            imgWeather = (ImageView) itemView.findViewById(R.id.w_img);
        }
    }
}
