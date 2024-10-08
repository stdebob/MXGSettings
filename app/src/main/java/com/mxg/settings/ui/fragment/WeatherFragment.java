// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment;

import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;

import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;

import moralnorm.preference.DropDownPreference;

public class WeatherFragment extends SettingsPreferenceFragment {

    DropDownPreference mCardDisplayType;
    @Override
    public int getContentResId() {
        return R.xml.weather;
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity)getActivity()).showRestartDialog(
            getResources().getString(R.string.weather),
            "com.miui.weather2"
        );
    }

    @Override
    public void initPrefs() {
        mCardDisplayType = findPreference("prefs_key_weather_card_display_type");
        mCardDisplayType.setVisible(isMoreHyperOSVersion(1f));
    }
}
