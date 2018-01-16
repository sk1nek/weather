package me.mjaroszewicz.weather;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by admin on 1/14/18.
 */

public class Utils {

    static String getStringFromUrl(String address){

        String s = null;

        try{
            URL url = new URL(address);
            InputStream is = url.openStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;

            while((line = in.readLine()) != null)
                sb.append(line);

            s = sb.toString();

        }catch(Throwable t){
            t.printStackTrace();
        }

        return s;
    }

    private static int celsiusToF(double temp){

        return (int) (32 + temp * 1.8);
    }

    static public String getTemperatureString(Weather w, String type){

        type = type.toLowerCase();

        switch (type) {
            case "kelvin":
                return w.getTemperature() + "K";
            case "fahrenheit":
                return celsiusToF(w.getTemperature() - 273.6) + "°F";
            default:
                return (int)(w.getTemperature() - 273.6) + "°C";
        }

    }

}
