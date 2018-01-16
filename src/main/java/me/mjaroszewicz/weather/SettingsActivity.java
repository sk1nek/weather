package me.mjaroszewicz.weather;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().add(android.R.id.content, new SettingsFragment()).commit();
    }
}
