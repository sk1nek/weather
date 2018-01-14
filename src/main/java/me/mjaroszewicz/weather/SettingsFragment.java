package me.mjaroszewicz.weather;

import android.app.Activity;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;

/**
 * Created by admin on 1/13/18.
 */

public class SettingsFragment extends PreferenceFragment {

    final private String CUSTOM_LIST = "custom_list";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);

        PreferenceScreen pc = getPreferenceScreen();

        ListPreference lp = setListPreferenceData(getActivity());
        pc.addPreference(lp);
        this.setPreferenceScreen(pc);


        this.setHasOptionsMenu(true);

    }

    protected ListPreference setListPreferenceData( Activity activity){

        CharSequence[] entries = {"A", "B", "C"};
        CharSequence[] entryValues = {"1", "2", "3"};


        ListPreference lp = new ListPreference(activity);

        lp.setEntries(entries);
        lp.setDefaultValue("1");
        lp.setTitle("Number of blahs");
        lp.setSummary(lp.getEntry());
        lp.setDialogTitle("XDDD");
        lp.setKey(CUSTOM_LIST);
        return lp;
    }
}
