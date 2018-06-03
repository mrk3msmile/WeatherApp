package com.example.vudou.weatherapp;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by vudou on 10/12/2017.
 */

public class Constain {
    public static final String API_KEY = "754bc27ab04480488f4d173beb0bb08c";
    public static final String DEGREE = "°";

    public static final String coord = "coord";
    public static final String lon = "lon";
    public static final String lat = "lat";

    public static final String weather = "weather";
    public static final String id = "id";
    public static final String main = "main";
    public static final String description = "description";
    public static final String icon = "icon";

    public static final String base = "base";

    public static final String temp = "temp";
    public static final String pressure = "pressure";
    public static final String humidity = "humidity";
    public static final String temp_min = "temp_min";
    public static final String temp_max = "temp_max";

    public static final String visibility = "visibility";
    public static final String speed = "speed";
    public static final String deg = "deg";

    public static final String clouds = "clouds";
    public static final String all = "all";

    public static final String dt = "dt";

    public static final String sys = "sys";
    public static final String type = "type";
    public static final String message = "message";
    public static final String country = "country";
    public static final String sunrise = "sunrise";
    public static final String sunset = "sunset";

    public static final String name = "name";
    public static final String cod = "cod";

    public static final String min = "min";
    public static final String max = "max";

    public static final String cnt = "cnt";
    public static final String list = "list";

    static double roundDecimals(double d) {
        return (int) Math.round(d);
    }

    static int roundDecimals(String strD) {
        Double d = Double.valueOf(strD);
        return (int) Math.round(d);
    }

    static int minTemperature(ArrayList<Weather> listWeather) {
        int min = listWeather.get(0).getMinTemp();
        for(int i=1; i< listWeather.size(); i++) {
            min = (min < listWeather.get(i).getMinTemp()) ? min: listWeather.get(i).getMinTemp();
        }
        return min;
    }

    static int maxTemperature(ArrayList<Weather> listWeather) {
        int max = listWeather.get(0).getMinTemp();
        for(int i=1; i< listWeather.size(); i++) {
            max = (max > listWeather.get(i).getMinTemp()) ? max: listWeather.get(i).getMinTemp();
        }
        return max;
    }

    static int getWeatherIconId(String status, String icon) {
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
}
