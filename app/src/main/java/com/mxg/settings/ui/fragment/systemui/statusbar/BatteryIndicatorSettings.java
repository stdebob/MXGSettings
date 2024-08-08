// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.systemui.statusbar;

import android.content.Intent;
import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;
import com.mxg.settings.utils.prefs.PrefsUtils;

import moralnorm.preference.ColorPickerPreference;
import moralnorm.preference.DropDownPreference;
import moralnorm.preference.Preference;

public class BatteryIndicatorSettings extends SettingsPreferenceFragment {

    DropDownPreference mBatteryIndicatorColor;
    ColorPickerPreference mBatteryIndicatorFullPower;
    ColorPickerPreference mBatteryIndicatorLowPower;
    ColorPickerPreference mBatteryIndicatorPowerSaving;
    ColorPickerPreference mBatteryIndicatorPowerCharging;
    Preference mBatteryIndicatorTest;

    @Override
    public int getContentResId() {
        return R.xml.system_ui_status_bar_battery_indicator;
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity)getActivity()).showRestartDialog(
            getResources().getString(R.string.system_ui),
            "com.android.systemui"
        );
    }

    @Override
    public void initPrefs() {

        mBatteryIndicatorColor = findPreference("prefs_key_system_ui_status_bar_battery_indicator_color");
        mBatteryIndicatorFullPower = findPreference("prefs_key_system_ui_status_bar_battery_indicator_color_full_power");
        mBatteryIndicatorLowPower = findPreference("prefs_key_system_ui_status_bar_battery_indicator_color_low_power");
        mBatteryIndicatorPowerSaving = findPreference("prefs_key_system_ui_status_bar_battery_indicator_color_power_saving");
        mBatteryIndicatorPowerCharging = findPreference("prefs_key_system_ui_status_bar_battery_indicator_color_power_charging");

        showBatteryIndicatorColor(Integer.parseInt(PrefsUtils.getSharedStringPrefs(getActivity(), mBatteryIndicatorColor.getKey(), "0")));

        mBatteryIndicatorColor.setOnPreferenceChangeListener((preference, o) -> {
            showBatteryIndicatorColor(Integer.parseInt((String) o));
            return true;
        });


        mBatteryIndicatorTest = findPreference("prefs_key_system_ui_status_bar_battery_indicator_test");
        mBatteryIndicatorTest.setOnPreferenceClickListener(preference -> {
            requireActivity().sendBroadcast(new Intent("moralnorm.module.BatteryIndicatorTest"));
            return true;
        });
    }

    private void showBatteryIndicatorColor(int vale) {
        boolean showBatteryIndicatorColor = vale != 2;
        mBatteryIndicatorFullPower.setVisible(showBatteryIndicatorColor);
        mBatteryIndicatorLowPower.setVisible(showBatteryIndicatorColor);
        mBatteryIndicatorPowerSaving.setVisible(showBatteryIndicatorColor);
        mBatteryIndicatorPowerCharging.setVisible(showBatteryIndicatorColor);
    }
}
