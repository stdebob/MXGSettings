// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.systemui;

import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;
import com.mxg.settings.utils.KillApp;

import moralnorm.preference.SwitchPreference;

public class NavigationSettings extends SettingsPreferenceFragment {
    SwitchPreference navigation;

    @Override
    public int getContentResId() {
        return R.xml.system_ui_navigation;
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity) getActivity()).showRestartDialog(
                getResources().getString(R.string.system_ui),
                "com.android.systemui"
        );
    }

    @Override
    public void initPrefs() {
        navigation = findPreference("prefs_key_system_ui_hide_navigation_bar");
        navigation.setOnPreferenceChangeListener((preference, o) -> {
            KillApp.killApps("com.miui.home", "com.android.systemui");
            return true;
        });
    }
}
