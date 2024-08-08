// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.systemui.statusbar;

import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;
import com.mxg.settings.utils.prefs.PrefsUtils;

import moralnorm.preference.DropDownPreference;
import moralnorm.preference.Preference;
import moralnorm.preference.PreferenceCategory;
import moralnorm.preference.SeekBarPreferenceEx;

public class ClockIndicatorSettings extends SettingsPreferenceFragment
    implements Preference.OnPreferenceChangeListener {

    DropDownPreference mClockModePreference;
    PreferenceCategory mDefault;
    PreferenceCategory mGeek;
    SeekBarPreferenceEx mWidth;

    @Override
    public int getContentResId() {
        return R.xml.system_ui_status_bar_clock_indicator;
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
        int mClockMode = Integer.parseInt(PrefsUtils.getSharedStringPrefs(getContext(), "prefs_key_system_ui_statusbar_clock_mode", "0"));
        mClockModePreference = findPreference("prefs_key_system_ui_statusbar_clock_mode");
        mDefault = findPreference("prefs_key_system_ui_statusbar_clock_default");
        mGeek = findPreference("prefs_key_system_ui_statusbar_clock_geek");
        mWidth = findPreference("prefs_key_system_ui_statusbar_clock_fixedcontent_width");

        setClockMode(mClockMode);
        mClockModePreference.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        if (preference == mClockModePreference) {
            setClockMode(Integer.parseInt((String) o));
        }
        return true;
    }

    private void setClockMode(int mode) {
        mWidth.setVisible(mode != 0);
        mDefault.setVisible(mode == 1);
        mGeek.setVisible(mode == 2);
    }
}
