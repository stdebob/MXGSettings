// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.systemui.statusbar;

import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;

import moralnorm.preference.DropDownPreference;
import moralnorm.preference.Preference;

public class DoubleLineNetworkSettings extends SettingsPreferenceFragment implements Preference.OnPreferenceChangeListener {

    DropDownPreference mIconTheme;
    DropDownPreference mIconStyle;
    @Override
    public int getContentResId() {
        return R.xml.system_ui_status_bar_doubleline_network;
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
        mIconTheme = findPreference("prefs_key_system_ui_statusbar_iconmanage_mobile_network_icon_theme");
        mIconStyle = findPreference("prefs_key_system_ui_status_mobile_network_icon_style");

        //setCanBeVisible(mBlurMode);
        mIconTheme.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        if (preference == mIconTheme) {
            setCanBeVisible(Integer.parseInt((String) o));
        }
        return true;
    }

    private void setCanBeVisible(int mode) {
        mIconStyle.setVisible(mode == 2);
    }
}
