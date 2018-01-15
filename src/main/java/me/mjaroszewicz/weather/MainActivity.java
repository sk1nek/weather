package me.mjaroszewicz.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private LocationService locationService;

    private WeatherService weatherService;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.locationService = new LocationService(this);
        this.weatherService = new WeatherService(locationService, this);
        this.sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        updateData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);

            return true;
        }

        if (id == R.id.action_refresh){
            updateData();
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateData(){
        TextView locationTextView = findViewById(R.id.main_location);
        locationTextView.setText(locationService.reverseGeocode());

        AsyncTask<Void, Void, Weather> weatherTask = new AsyncTask<Void, Void, Weather>() {
            @Override
            protected Weather doInBackground(Void... voids) {
                return weatherService.getCurrentWeather();
            }
        };
        weatherTask.execute();
        Weather currentWeather;
        try{
            currentWeather = weatherTask.get();
            TextView tempTextView = findViewById(R.id.main_temp);


            tempTextView.setText(getTemperatureString(currentWeather, sharedPreferences.getString("temperature_scale", "celsius")));

        }catch (Throwable t){
            t.printStackTrace();
            Toast.makeText(this, "Error has occured during data fetch. \nTry again later.", Toast.LENGTH_LONG);
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

    }

    private int celsiusToF(double temp){

        return (int) (32 + temp * 1.8);
    }

    private String getTemperatureString(Weather w, String type){

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
