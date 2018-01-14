package me.mjaroszewicz.weather;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private LocationService locationService;

    private WeatherService weatherService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.locationService = new LocationService(this);
        this.weatherService = new WeatherService(locationService, this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
            tempTextView.setText(currentWeather.getDescription());

        }catch (Throwable t){
            t.printStackTrace();
        }


    }

}
