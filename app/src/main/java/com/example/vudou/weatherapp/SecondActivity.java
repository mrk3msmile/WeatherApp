package com.example.vudou.weatherapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SecondActivity extends AppCompatActivity {
    private ImageView btnBack, btnAdd;
    private TextView title;
    private RecyclerView recyclerView;
    private ArrayList<Weather> listWeather;
    private WeatherListAdapter adapter;
    private DatabaseHelper db;
    private ArrayList<City> listCity;
    private RequestQueue requestQueue;
    private CustomDialog customDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setContructor();
        setEvent();
        showCityWeather();
    }

    public void getCurrentData(String data) {
        String urlName = "http://api.openweathermap.org/data/2.5/weather?q="+ data +"&units=metric&appid=754bc27ab04480488f4d173beb0bb08c";
        StringRequest stringRequest = getDataToday(urlName);
        Log.d("request: ", stringRequest+"");
        requestQueue.add(stringRequest);
    }

    public void getCurrentData(long cityId) {
        String data = String.valueOf(cityId);
        String urlName = "http://api.openweathermap.org/data/2.5/weather?q="+ data +"&units=metric&appid=754bc27ab04480488f4d173beb0bb08c";
        StringRequest stringRequest = getDataToday(urlName);
        requestQueue.add(stringRequest);

    }

    public StringRequest getDataToday(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Result", response);
                        try {
                            // NAME JSSONObject
                            // City name
                            JSONObject jsonObject = new JSONObject(response);
                            String name = jsonObject.getString(Constain.name);
                            String id = jsonObject.getString("id");
                            int currentId = Integer.valueOf(id);

                            // SYS JSSONObject
                            JSONObject jsonObjectSys = jsonObject.getJSONObject(Constain.sys);
                            // Country
                            String country = jsonObjectSys.getString(Constain.country);

                            // DT JSSONObject
                            String day = jsonObject.getString(Constain.dt);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM");
                            long l3 = Long.valueOf(day);
                            Date date = new Date(l3*1000L);
                            String Day = simpleDateFormat.format(date);

                            // MAIN JSSONObject
                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            // Temperature
                            String temp = jsonObjectMain.getString("temp");
                            temp = String.valueOf(Constain.roundDecimals(temp));

                            // Min temperature
                            String temp_min = jsonObjectMain.getString("temp_min");
                            int min = Constain.roundDecimals(temp_min);
                            // Max temperature
                            String temp_max = jsonObjectMain.getString("temp_max");
                            int max = Constain.roundDecimals(temp_max);

                            // WEATHER JSSONObject
                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            // Status
                            String status = jsonObjectWeather.getString("main");
                            // Description
                            String description = jsonObjectWeather.getString("description");
                            // Icon
                            String icon = jsonObjectWeather.getString("icon");
                            int picRes = getWeatherIconId(status, icon);

                            Weather w = new Weather(name, Day, temp, temp_min, temp_max, picRes, status, description);
                            Log.d("weather: ", w.toString());
                            listWeather.add(w);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        return stringRequest;
    }


    public int getWeatherIconId(String status, String icon) {
        boolean isDay;
        if(icon.contains("d")) {
            isDay = true;
        } else {
            isDay = false;
        }
        int picRes = 0;
        switch (status) {
            case Weather.CLEAR:
                if(isDay)
                    picRes = R.drawable.ic_sun;
                else
                    picRes = R.drawable.ic_moon;
                break;
            case Weather.CLOUDS:
                if(isDay)
                    picRes = R.drawable.ic_cloud_day;
                else
                    picRes = R.drawable.ic_cloud_night;
                break;
            case Weather.RAIN:
                if(isDay)
                    picRes = R.drawable.ic_rain_day;
                else
                    picRes = R.drawable.ic_rain_night;
                break;
            case Weather.THUNDERSTORM:
                picRes = R.drawable.ic_storm;
                break;
            case Weather.DRIZZLE:
                picRes = R.drawable.ic_drizzle;
                break;
            case Weather.SNOW:
                picRes = R.drawable.ic_snow;
                break;
            case Weather.MIST:
                if(isDay) {
                    picRes = R.drawable.ic_mist_day;
                } else {
                    picRes = R.drawable.ic_mist_night;
                }
                break;
            case Weather.ATMOSPHERE:
                picRes = R.drawable.ic_snow;
                break;
            case Weather.ADDITIONAL:
                picRes = R.drawable.ic_clouds;
                break;
            case Weather.EXTREME:
                picRes = R.drawable.ic_rain;
                break;
            default:
                picRes = R.drawable.ic_hail;
                break;
        }
        return Integer.valueOf(picRes);
    }

    public void showCityWeather() {
        listCity = db.getAllCity();
        for(int i=0; i<listCity.size(); i++) {
            getCurrentData(listCity.get(i).getName());
        }
    }

    public void setContructor() {
        btnBack = (ImageView) findViewById(R.id.second_back);
        btnAdd = (ImageView) findViewById(R.id.second_add);
        title = (TextView) findViewById(R.id.second_title);
        recyclerView = (RecyclerView) findViewById(R.id.second_recyclerView);
        requestQueue = Volley.newRequestQueue(SecondActivity.this);
        db = new DatabaseHelper(getApplicationContext());
        listWeather = new ArrayList<>();
        listCity = new ArrayList<>();
        adapter = new WeatherListAdapter(this, listWeather);
        adapter.setHasStableIds(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(SecondActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    public void setEvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondActivity.this, MainActivity.class));
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog = new CustomDialog(SecondActivity.this);
                customDialog.show();
            }
        });
    }
}
