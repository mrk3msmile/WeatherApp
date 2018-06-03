package com.example.vudou.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.example.vudou.weatherapp.Weather.*;

public class MainActivity extends AppCompatActivity {
    private AutoCompleteTextView autoText;
    private TextView txtTemp, txtTempMimax,txtStatus, txtCity, txtHumidty, txtCloud, txtWind, txtDateUpdate, txtSunSet, txtSunRise;
    private ImageView imgWeather, btnSearch, next, btnAdd;
    private RecyclerView recyclerWeather;
    private WeatherAdapter weatherAdapter;
    private ArrayList<Weather> listWeather;
    private RequestQueue requestQueue;
    private long currentId;
    private String cityName = "";
    private Switch aSwitch;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContructor();
        setEvent();
        getCurrentData("Ha noi");
        if(aSwitch.isChecked()) {
            get5DaysData("Ha noi");
        } else {
            get24hData("Ha noi");
        }
    }

    public void getCurrentData(String data) {
        String urlName = "http://api.openweathermap.org/data/2.5/weather?q="+ data +"&units=metric&appid=754bc27ab04480488f4d173beb0bb08c";
        StringRequest stringRequest = getDataToday(urlName);
        requestQueue.add(stringRequest);
    }

    public void getCurrentData(long cityId) {
        String data = String.valueOf(cityId);
        String urlName = "http://api.openweathermap.org/data/2.5/weather?q="+ data +"&units=metric&appid=754bc27ab04480488f4d173beb0bb08c";
        StringRequest stringRequest = getDataToday(urlName);
        requestQueue.add(stringRequest);

    }

    public void get24hData(String data) {
        String urlWeek = "http://api.openweathermap.org/data/2.5/forecast?q=" + data + "&cnt=8&units=metric&appid=754bc27ab04480488f4d173beb0bb08c";
        StringRequest stringRequestWeek = getData24Hours(urlWeek);
        requestQueue.add(stringRequestWeek);
    }

    public void get24hData(long cityId) {
        String data = String.valueOf(cityId);
        String urlWeek = "http://api.openweathermap.org/data/2.5/forecast?q=" + data + "&cnt=8&units=metric&appid=754bc27ab04480488f4d173beb0bb08c";
        StringRequest stringRequestWeek = getData24Hours(urlWeek);
        requestQueue.add(stringRequestWeek);
    }


    public void get5DaysData(String data) {
        String urlWeek = "http://api.openweathermap.org/data/2.5/forecast?q=" + data + "&units=metric&appid=754bc27ab04480488f4d173beb0bb08c";
        StringRequest stringRequestWeek = getData5Days(urlWeek);
        requestQueue.add(stringRequestWeek);
    }

    public void get5DaysData(long cityId) {
        String data = String.valueOf(cityId);
        String urlWeek = "http://api.openweathermap.org/data/2.5/forecast?q=" + data + "&units=metric&appid=754bc27ab04480488f4d173beb0bb08c";
        StringRequest stringRequestWeek = getData5Days(urlWeek);
        requestQueue.add(stringRequestWeek);
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
                        currentId = Long.valueOf(id);
                        cityName = name;
//                        Toast.makeText(MainActivity.this, currentId +"", Toast.LENGTH_SHORT).show();

                        // SYS JSSONObject
                        JSONObject jsonObjectSys = jsonObject.getJSONObject(Constain.sys);
                        // Country
                        String country = jsonObjectSys.getString(Constain.country);
                        txtCity.setText(name + ", " + country);
                        autoText.setText("");
                        //Sun rise

                        String sunRise = jsonObjectSys.getString(Constain.sunrise);
                        long l1 = Long.valueOf(sunRise);
                        Date timeRise = new Date(l1*1000L);
                        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm");
                        String SunRise = simpleTimeFormat.format(timeRise);
                        txtSunRise.setText("Sun rise: " + SunRise);
                        //Sun set
                        String sunSet = jsonObjectSys.getString(Constain.sunset);
                        long l2 = Long.valueOf(sunSet);
                        Date timeSet = new Date(l2*1000L);
                        String SunSet = simpleTimeFormat.format(timeSet);
                        txtSunSet.setText("Sun set: " + SunSet);

                        // DT JSSONObject
                        String day = jsonObject.getString(Constain.dt);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        long l3 = Long.valueOf(day);
                        Date date = new Date(l3*1000L);
                        String Day = simpleDateFormat.format(date);
                        txtDateUpdate.setText(Day);

                        // MAIN JSSONObject
                        JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                        // Temperature
                        String temp = jsonObjectMain.getString("temp");
                        temp = String.valueOf(Constain.roundDecimals(temp));
                        txtTemp.setText(temp + "°");

                        // Humidity
                        String humidity = jsonObjectMain.getString("humidity");
                        txtHumidty.setText(humidity + "%");
                        // Min temperature
                        String temp_min = jsonObjectMain.getString("temp_min");
                        int min = Constain.roundDecimals(temp_min);
                        // Max temperature
                        String temp_max = jsonObjectMain.getString("temp_max");
                        int max = Constain.roundDecimals(temp_max);
                        txtTempMimax.setText(min + "°C - " + max + "°C");

                        // WEATHER JSSONObject
                        JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                        // Status
                        String status = jsonObjectWeather.getString("main");
                        // Description
                        String description = jsonObjectWeather.getString("description");
                        // Icon
                        String icon = jsonObjectWeather.getString("icon");
                        txtStatus.setText(description);
                        imgWeather.setImageResource(getWeatherIconId(status, icon));
//                        weatherIcon(status, description, icon);

                        // WIND JSSONObject
                        JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                        // Wind speed
                        String wind = jsonObjectWind.getString("speed");
                        txtWind.setText(wind + " m/s");

                        // CLOUDS JSSONObject
                        JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
                        // Percent cloud
                        String cloud = jsonObjectCloud.getString("all");
                        txtCloud.setText(cloud + "%");

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

    public StringRequest getData5Days(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("result week: ", response);
                        try {
                            listWeather.clear();
                            JSONObject jsonObject = new JSONObject(response);

                            //City JsonObject
                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                            String name = jsonObjectCity.getString("name");
                            String id = jsonObjectCity.getString("id");
                            currentId = Long.valueOf(id);

                            // List JsonArray
                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");


                            for(int i=0; i<jsonArrayList.length(); i=i+8) {
                                JSONObject json = jsonArrayList.getJSONObject(i);

                                //2017-10-12 09:00:00
                                String dt_txt = json.getString("dt_txt");
                                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                DateFormat outputFormat = new SimpleDateFormat("EEE");
                                Date date = inputFormat.parse(dt_txt);
                                String day = outputFormat.format(date);


                                // Main Json
                                JSONObject jsonObjectMain = json.getJSONObject("main");
                                // Max, min temperature
                                String temp_min = jsonObjectMain.getString("temp_min");
                                String temp_max = jsonObjectMain.getString("temp_max");
                                int min = Constain.roundDecimals(temp_min);
                                int max = Constain.roundDecimals(temp_max);
                                String temperature = String.format(min + "°/" + max + "°");

                                // WEATHER JSONArray
                                JSONArray jsonArrayWeather = json.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);

                                String status = jsonObjectWeather.getString("main");
                                String desc = jsonObjectWeather.getString("description");
                                String icon = jsonObjectWeather.getString("icon");
                                int picRes = getWeatherIconId(status, icon);

                                Weather w = new Weather(day, temp_min, temp_max, picRes, status, desc);
                                w.setTemperature(temperature);
                                listWeather.add(w);

                            }
                            weatherAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
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

    public StringRequest getData24Hours(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("result week: ", response);
                        try {
                            listWeather.clear();

                            JSONObject jsonObject = new JSONObject(response);

                            //City JsonObject
                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                            String name = jsonObjectCity.getString("name");
                            String id = jsonObjectCity.getString("id");
                            currentId = Long.valueOf(id);

                            // List JsonArray
                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");


                            for(int i=0; i<jsonArrayList.length(); i++) {
                                JSONObject json = jsonArrayList.getJSONObject(i);

                                //2017-10-12 09:00:00
                                String dt_txt = json.getString("dt_txt");
                                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                DateFormat outputFormat = new SimpleDateFormat("HH");
                                Date date = inputFormat.parse(dt_txt);
                                int aa = Integer.valueOf(outputFormat.format(date));
                                String day = String.format(aa + "h");


                                // Main Json
                                JSONObject jsonObjectMain = json.getJSONObject("main");
                                // Max, min temperature
                                String temp = jsonObjectMain.getString("temp");
                                String temp_min = jsonObjectMain.getString("temp_min");
                                String temp_max = jsonObjectMain.getString("temp_max");

                                // WEATHER JSONArray
                                JSONArray jsonArrayWeather = json.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);

                                String status = jsonObjectWeather.getString("main");
                                String desc = jsonObjectWeather.getString("description");
                                String icon = jsonObjectWeather.getString("icon");
                                int picRes = getWeatherIconId(status, icon);

                                Weather w = new Weather(day, temp_min, temp_max, picRes, status, desc);
                                int tempe1 = Constain.roundDecimals(temp);
                                w.setTemperature(tempe1 + "°");
                                listWeather.add(w);

                            }
                            weatherAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
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

    public void setEvent() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = autoText.getText().toString();
                getCurrentData(city);
                boolean check = aSwitch.isChecked();
                if(check) {
                    aSwitch.setText("5 Days");
                    get5DaysData(city);

                } else {
                    aSwitch.setText("24 Hours");
                    get24hData(city);
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new DatabaseHelper(getApplicationContext());
                if(db.checkExist(currentId)) {
                    Toast.makeText(MainActivity.this, cityName + " has been already added!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, cityName + " is added!", Toast.LENGTH_SHORT).show();
                    db.addCity(new City(currentId, cityName));
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                ArrayList<City> listCity = new ArrayList<>();
//                listCity = db.getAllCity();
                startActivity(new Intent(MainActivity.this, SecondActivity.class));

            }
        });

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = aSwitch.isChecked();
                Toast.makeText(MainActivity.this, currentId+"", Toast.LENGTH_SHORT).show();
                if(check) {
                    aSwitch.setText("5 Days");
//                    aSwitch.setChecked(false);
                    listWeather.clear();
                    get5DaysData("ha noi");

                } else {
                    aSwitch.setText("24 Hours");
//                    aSwitch.setChecked(true);
                    listWeather.clear();
                    get24hData("ha noi");
                }
            }

        });

    }

    public void chageText(int colorId) {
        txtHumidty.setTextColor(getResources().getColor(colorId));
        txtCloud.setTextColor(getResources().getColor(colorId));
        txtWind.setTextColor(getResources().getColor(colorId));
        txtSunRise.setTextColor(getResources().getColor(colorId));
        txtSunSet.setTextColor(getResources().getColor(colorId));
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
                picRes = R.drawable.ic_clouds;
                break;
            case Weather.RAIN:
                picRes = R.drawable.ic_rain;
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


    public void setContructor() {
        autoText = (AutoCompleteTextView) findViewById(R.id.autoText);
        txtTemp = (TextView) findViewById(R.id.txtTemp);
        txtTempMimax = (TextView) findViewById(R.id.txtTempMimax);
        txtStatus  = (TextView) findViewById(R.id.txtStatus);
        txtCity = (TextView) findViewById(R.id.txtCity);
        txtHumidty  = (TextView) findViewById(R.id.txtHumidty);
        txtCloud = (TextView) findViewById(R.id.txtCloud);
        txtWind = (TextView) findViewById(R.id.txtWind);
        txtDateUpdate = (TextView) findViewById(R.id.txtDateUpdate);
        txtSunSet = (TextView) findViewById(R.id.txtSunSet);
        txtSunRise = (TextView) findViewById(R.id.txtSunRise);
        imgWeather = (ImageView) findViewById(R.id.imgWeather);
        btnAdd = (ImageView) findViewById(R.id.main_add);
        btnSearch = (ImageView) findViewById(R.id.btnSearch);
        next = (ImageView) findViewById(R.id.next);
        aSwitch = (Switch) findViewById(R.id.main_switch);
        recyclerWeather = (RecyclerView) findViewById(R.id.recyclerWeather);
        listWeather = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(MainActivity.this);

        weatherAdapter = new WeatherAdapter(getApplicationContext(), listWeather);
        weatherAdapter.setHasStableIds(true);

        recyclerWeather.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerWeather.setAdapter(weatherAdapter);
    }
}
