package me.mjaroszewicz.weather;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

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
}
