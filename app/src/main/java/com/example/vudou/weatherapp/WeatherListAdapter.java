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
 * Created by vudou on 10/12/2017.
 */

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.RecyclerViewHolder>{
    private Context context;
    private ArrayList<Weather> listWeather;

    public WeatherListAdapter(Context context, ArrayList<Weather> listWeather) {
        this.context = context;
        this.listWeather = listWeather;
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

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.layout_weather_vertical, parent, false);
        return new RecyclerViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.city.setText(listWeather.get(position).getCity()+"");
        holder.date.setText(listWeather.get(position).getDate()+"");
        holder.temp.setText(listWeather.get(position).getCurrentTemp()+"°");

//        holder.temp_mimax.setText("40° - 45°");
        holder.temp_mimax.setText(listWeather.get(position).getMimax()+"");
        holder.status.setText(listWeather.get(position).getStatus()+"");
        holder.img.setImageResource(listWeather.get(position).getPicRes());
    }

    @Override
    public int getItemCount() {
        return listWeather.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView city, date, temp, temp_mimax, status;
        private ImageView img;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            city = (TextView) itemView.findViewById(R.id.list_city);
            date = (TextView) itemView.findViewById(R.id.list_date);
            temp = (TextView) itemView.findViewById(R.id.list_temp);
            temp_mimax = (TextView) itemView.findViewById(R.id.list_temp_mimax);
            status = (TextView) itemView.findViewById(R.id.list_status);
            img = (ImageView) itemView.findViewById(R.id.list_img);
        }
    }
}
