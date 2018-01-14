package me.mjaroszewicz.weather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by admin on 1/13/18.
 */

public class SettingsFragment extends PreferenceFragment {

    final private int SELECT_LOCATION_ACTION = 12321;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);

        PreferenceScreen pc = getPreferenceScreen();

        Preference p = buildLocationPreference(getActivity());
        pc.addPreference(p);


        this.setPreferenceScreen(pc);
        this.setHasOptionsMenu(true);

    }

    private Preference buildLocationPreference(final Activity activity){

        Preference ret = new Preference(activity);

        ret.setEnabled(true);
        ret.setTitle("Location");
        ret.setSummary("Select your location on map.");

        ret.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {

                Intent i = new Intent(activity, SelectLocationActivity.class);
                startActivityForResult(i, SELECT_LOCATION_ACTION);

                return true;
            }
        });

        return ret;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == SELECT_LOCATION_ACTION){

            Bundle extras = data.getExtras();

            Toast.makeText(getActivity(), extras.getDouble("lat") + "  " + extras.getDouble("lng"), Toast.LENGTH_LONG).show();

            SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);

            sp.edit().putString("location_lattitude", Double.toString(extras.getDouble("lat"))).putString("location_longtitude", Double.toString(extras.getDouble("lng"))).apply();
        }


    }
}
