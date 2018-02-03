package me.mjaroszewicz.weather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import eltos.simpledialogfragment.SimpleDialog;
import eltos.simpledialogfragment.color.SimpleColorDialog;

public class SettingsActivity extends AppCompatActivity implements SimpleDialog.OnDialogResultListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().add(android.R.id.content, new SettingsFragment(), "settings").commit();

    }

    @Override
    public boolean onResult(@NonNull String dialogTag, int which, @NonNull Bundle extras) {


        if (dialogTag.equals("bgcolor")) {

            //result is signed integer, gonna convert it to 6 char hex
            int color = extras.getInt("SimpleColorDialogcolor");
            String hexColor = String.format("#%06X", (0xFFFFFF & color));

            //putting color into shared prefs
            SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
            sharedPreferences.edit().putString("background_color", hexColor).apply();

            //recreating main activity
            MainActivity.getInstance().recreate();

        }

        if(dialogTag.equals("tbcolor")){

            //integer to hex
            int color = extras.getInt("SimpleColorDialogcolor");
            String hexColor = String.format("#%06x", (0xFFFFFF & color));

            //color into prefs
            SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
            sharedPreferences.edit().putString("toolbar_color", hexColor).apply();

            //recreating main activity
            MainActivity.getInstance().recreate();


        }
        return true;
    }
}
