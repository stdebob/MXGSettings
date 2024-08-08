// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.various;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.SettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;

public class LocationSimulationActivity extends SettingsActivity {

    private static SharedPreferences mSharedPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImmersionMenuEnabled(true);
        setFragment(new LocationSimulationFragment());
    }

    public static class LocationSimulationFragment extends SettingsPreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mSharedPrefs = getPreferenceManager().getSharedPreferences();
            setSharedPreferenceChanged();
        }

        @Override
        public int getContentResId() {
            return R.xml.various_location_simulation;
        }

        @Override
        public void initPrefs() {
            super.initPrefs();
        }


        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            setSharedPreferenceChanged();
        }

        private void setSharedPreferenceChanged() {

        }

        @Override
        public void onResume() {
            super.onResume();
            mSharedPrefs.registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            mSharedPrefs.unregisterOnSharedPreferenceChangeListener(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_location_simulation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.location_simulation_data) {

        }
        return super.onOptionsItemSelected(item);
    }
}
