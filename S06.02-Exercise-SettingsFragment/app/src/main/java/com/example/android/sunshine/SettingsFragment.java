package com.example.android.sunshine;


import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.myprefs);

        SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
        int count = getPreferenceScreen().getPreferenceCount();
        for (int i=0;i<count;i++){
            Preference p = getPreferenceScreen().getPreference(i);
            if (!(p instanceof CheckBoxPreference)) {
                setPreferenceSummary(p,sp.getString(p.getKey(),""));
            }
        }

    }

    private void setPreferenceSummary(Preference p,Object mValue){
        if (p instanceof ListPreference){
            ListPreference lp = (ListPreference)p;
            int indexOfEntry=lp.findIndexOfValue(mValue.toString());
            if (indexOfEntry>=0) lp.setSummary(lp.getEntries()[indexOfEntry]);
        }else {
            p.setSummary(mValue.toString());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference p = findPreference(key);
        if (p!= null && !(p instanceof CheckBoxPreference)) {
            setPreferenceSummary(p,sharedPreferences.getString(key,""));
        }
    }
}
