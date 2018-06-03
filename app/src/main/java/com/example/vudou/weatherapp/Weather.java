package com.example.vudou.weatherapp;

/**
 * Created by vudou on 10/11/2017.
 */

public class Weather {
    public static final String CLEAR = "Clear";
    public static final String RAIN = "Rain";
    public static final String SNOW = "Snow";
    public static final String MIST = "Mist";
    public static final String THUNDERSTORM = "Thunderstorm";
    public static final String DRIZZLE = "Drizzle";
    public static final String ATMOSPHERE = "Atmosphere";
    public static final String CLOUDS = "Clouds";
    public static final String EXTREME = "Extreme";
    public static final String ADDITIONAL = "Additional";

    private String city;
    private String date;
    private String temperature;
    private int currentTemp;
    private int minTemp;
    private int maxTemp;
    private int picRes;
    private String status;
    private String desc;

    public Weather(String date, String minTemp, String maxTemp, int picRes, String status, String desc) {
        this.date = date;
        this.minTemp = Constain.roundDecimals(minTemp);
        this.maxTemp = Constain.roundDecimals(maxTemp);
        this.picRes = picRes;
        this.status = status;
        this.desc = desc;
    }

    public Weather(String city, String date, String currentTemp, String minTemp, String maxTemp, int picRes, String status, String desc) {
        this.city = city;
        this.date = date;
        this.currentTemp = Constain.roundDecimals(currentTemp);
        this.minTemp = (Constain.roundDecimals(minTemp));
        this.maxTemp = (Constain.roundDecimals(maxTemp));
        this.picRes = picRes;
        this.status = status;
        this.desc = desc;
    }

//    public Weather() {
//        this.city = "Ha noi";
//        this.date = "22/12";
//        this.currentTemp = 22;
//        this.minTemp = 30;
//        this.maxTemp = 34;
//        this.picRes = R.drawable.ic_storm;
//        this.desc = "Storm";
//        this.status = "abcd";
//    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = Integer.valueOf(minTemp);
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public void setMaxTemp(String maxTemp) { this.maxTemp = Integer.valueOf(maxTemp);}

    public int getPicRes() {
        return picRes;
    }

    public void setPicRes(int picRes) {
        this.picRes = picRes;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return String.format(this.date + " - " + this.status);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(int currentTemp) {
        this.currentTemp = currentTemp;
    }

    public void setCurrentTemp(String currentTemp) {
        this.currentTemp = Integer.valueOf(currentTemp);
    }

    public String getMimax() {
        String mimax = String.valueOf(getMinTemp() + "°C/" + getMaxTemp() + "°C");
        return mimax;
    }
}

