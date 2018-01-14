package me.mjaroszewicz.weather;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 1/14/18.
 */

public class LocationService {

    private final String DEFAULT_LAT = "40.7142205";

    private final String DEFAULT_LNG = "-73.9612903";

    private static String API_KEY;

    private final Activity context;

    private static SharedPreferences sharedPreferences;

    LocationService(Activity context) {
        this.context = context;
        init();
    }

    private void init(){
        API_KEY = context.getResources().getString(R.string.google_maps_key);
        sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    /**
     * Does reverse geocoding of coordinates saved in SharedPreferences by keys "location_latitude" and "location_longitude"
     *
     * @return approximation of address
     */
    public String reverseGeocode(){


        String lat = sharedPreferences.getString("location_latitude", DEFAULT_LAT);
        String lng = sharedPreferences.getString("location_longitude", DEFAULT_LNG);

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/geocode/json?");
        sb.append("latlng=")
                .append(lat)
                .append(',')
                .append(lng)
                .append("&key=")
                .append(API_KEY);


        @SuppressLint("StaticFieldLeak")
        AsyncTask<String, Void, String> at = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return Utils.getStringFromUrl(strings[0]);
            }
        };

        at.execute(sb.toString());

        String query = null;

        try{
            query = at.get();
        }catch(Throwable t){
            Log.w("ERROR", t.getLocalizedMessage() + " ");
        }

        ArrayList<String> formattedAddresses = new ArrayList<>();


        try {
            JSONObject jsonObject = new JSONObject(query);
            JSONArray results = jsonObject.getJSONArray("results");

            for(int i = 0 ; i < results.length(); i++)
                formattedAddresses.add(results.getJSONObject(i).getString("formatted_address"));

        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }


        if(formattedAddresses.size() >= 2)
            return formattedAddresses.get(2);
        else
            return formattedAddresses.get(formattedAddresses.size() - 1);

    }

    public String getLat(){
        return sharedPreferences.getString("location_latitude", DEFAULT_LAT);
    }

    public String getLng(){
        return sharedPreferences.getString("location_longtitude", DEFAULT_LNG);
    }

}
