package com.example.vudou.weatherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by vudou on 10/18/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CityDB";
    private static final String TABLE_CITY = "table_city";
    private static final String KEY_CITY_ID = "city_id";
    private static final String KEY_CITY_NAME = "city_name";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SONG_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CITY + " ("
                + KEY_CITY_ID + " INTEGER PRIMARY KEY, "
                + KEY_CITY_NAME + " TEXT)";

        db.execSQL(CREATE_SONG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addCity(City city) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_CITY_ID, city.getId());
        contentValues.put(KEY_CITY_NAME, city.getName());
        db.insert(TABLE_CITY, null, contentValues);
    }

    public ArrayList<City> getAllCity() {
        ArrayList<City> listCity = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_CITY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(0));
                city.setName(cursor.getString(1));

                listCity.add(city);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listCity;
    }

    public boolean checkExist(long id) {
        String query = "SELECT * FROM " + TABLE_CITY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                if(id==cursor.getInt(0)) {
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return false;
    }
 }
