package me.mjaroszewicz.weather;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private LocationService locationService;

    private WeatherService weatherService;

    private SharedPreferences sharedPreferences;

    private static MainActivity instance;

    private static Long lastUpdateTimeMilis = System.currentTimeMillis();

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.locationService = new LocationService(this);
        this.weatherService = new WeatherService(locationService, this);
        this.sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        instance = this;

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //changing background color
        RelativeLayout main = findViewById(R.id.main);
        String hexColor = sharedPreferences.getString("background_color", "#303f9f");
        main.setBackgroundColor(Color.parseColor(hexColor));


        updateData();
        initUpdateExecutor();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);

            return true;
        }

        if (id == R.id.action_refresh) {
            updateData();
        }

        return super.onOptionsItemSelected(item);
    }

    void updateData() {
        TextView locationTextView = findViewById(R.id.main_location);
        locationTextView.setText(locationService.reverseGeocode());

        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, Weather> weatherTask = new AsyncTask<Void, Void, Weather>() {
            @Override
            protected Weather doInBackground(Void... voids) {
                return weatherService.getCurrentWeather();
            }
        };
        weatherTask.execute();
        Weather currentWeather;
        try {
            currentWeather = weatherTask.get();
            TextView tempTextView = findViewById(R.id.main_temp);
            tempTextView.setText(Utils.getTemperatureString(currentWeather, sharedPreferences.getString("temperature_scale", "celsius")));

        } catch (Throwable t) {
            t.printStackTrace();
            Toast.makeText(this, "Error has occured during data fetch. \nTry again later.", Toast.LENGTH_LONG).show();
            return;
        }

        Resources resources = getResources();

        String icon = currentWeather.getIcon();
        Log.w("ICON", icon + " ");
        int iconId = resources.getIdentifier("ic_" + icon, "drawable", getPackageName());

        ImageView iconView = findViewById(R.id.main_icon_view);
        iconView.setImageDrawable(resources.getDrawable(iconId));

        TextView weatherDescriptionTextView = findViewById(R.id.main_weather_description);
        String description = currentWeather.getDescription();
        String first = description.substring(0, 1);
        description = first.toUpperCase() + description.substring(1);
        weatherDescriptionTextView.setText(description);

        TextView atmosphericPressureTextView = findViewById(R.id.main_weather_pressure);
        String pressureText = "Pressure: " + (int) currentWeather.getPressure() + " hPa";
        atmosphericPressureTextView.setText(pressureText);

        TextView humidityTextView = findViewById(R.id.main_weather_humidity);
        String humidityText = "Humidity: " + currentWeather.getHumidity() + "%";
        humidityTextView.setText(humidityText);

        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, List<Weather>> downloadForecastTask = new AsyncTask<Void, Void, List<Weather>>() {
            @Override
            protected List<Weather> doInBackground(Void... voids) {
                return weatherService.getFiveDayForecast();
            }
        };
        downloadForecastTask.execute();

        ForecastRecyclerAdapter forecastRecyclerAdapter;
        try {
            forecastRecyclerAdapter = new ForecastRecyclerAdapter(this, downloadForecastTask.get());
        } catch (Throwable t) {
            t.printStackTrace();
            return;
        }
        RecyclerView recyclerView = findViewById(R.id.main_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(forecastRecyclerAdapter);

        lastUpdateTimeMilis = System.currentTimeMillis();

    }

    private void initUpdateExecutor(){
        executorService.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        if((System.currentTimeMillis() - lastUpdateTimeMilis) > 1000 * 60 * 50)
                            updateData();
                    }
                },
                0, 10, TimeUnit.MINUTES

        );
    }


    static MainActivity getInstance() {
        return instance;
    }

}
