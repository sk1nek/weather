package me.mjaroszewicz.weather;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by admin on 1/14/18.
 */

public class WeatherService {

    private final String API_KEY;

    private LocationService locationService;

    private Activity context;

    WeatherService(LocationService locationService, Activity context){
        this.locationService = locationService;
        this.context = context;
        API_KEY = context.getResources().getString(R.string.openweather_api_key);

    }


    Weather getCurrentWeather(){

        String lat = locationService.getLat();
        String lng = locationService.getLng();

        StringBuilder sb = new StringBuilder("http://api.openweathermap.org/data/2.5/weather?");

        sb
                .append("lat=")
                .append(lat)
                .append("&lon=")
                .append(lng)
                .append("&appid=")
                .append(API_KEY);

        Log.w("URL", sb.toString() + " ");

        String json = Utils.getStringFromUrl(sb.toString());

        Weather ret = null;

        try{
            ret = new Weather();

            JSONObject root = new JSONObject(json);

            JSONArray arr = root.getJSONArray("weather");
            JSONObject obj = arr.getJSONObject(0);

            ret.setId(obj.getInt("id"));
            ret.setDescription(obj.getString("description"));
            ret.setIcon(obj.getString("icon"));

            obj = root.getJSONObject("main");

            ret.setTemperature(obj.getDouble("temp"));
            ret.setPressure(obj.getDouble("pressure"));
            ret.setHumidity(obj.getInt("humidity"));
            ret.setTemperatureMin(obj.getDouble("temp_min"));
            ret.setTemperatureMax(obj.getDouble("temp_max"));

            obj = root.getJSONObject("wind");

            ret.setWindSpeed(obj.getDouble("speed"));
            ret.setWindDegrees(obj.getDouble("deg"));

            obj = root.getJSONObject("clouds");

            ret.setClouds(obj.getInt("all"));

        }catch (Throwable t){
            t.printStackTrace();
            return null;
        }

        return ret;
    }




}
