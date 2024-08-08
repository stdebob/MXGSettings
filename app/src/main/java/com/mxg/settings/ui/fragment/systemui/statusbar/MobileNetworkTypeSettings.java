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
import moralnorm.preference.SwitchPreference;

public class MobileNetworkTypeSettings extends SettingsPreferenceFragment
    implements Preference.OnPreferenceChangeListener {

    DropDownPreference mMobileMode;
    PreferenceCategory mMobileTypeGroup;
    SwitchPreference mMobileType;

    @Override
    public int getContentResId() {
        return R.xml.system_ui_status_bar_mobile_network_type;
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
        int mobileMode = Integer.parseInt(PrefsUtils.getSharedStringPrefs(getContext(), "prefs_key_system_ui_status_bar_icon_show_mobile_network_type", "0"));
        mMobileMode = findPreference("prefs_key_system_ui_status_bar_icon_show_mobile_network_type");
        mMobileType = findPreference("prefs_key_system_ui_statusbar_mobile_type_enable");
        mMobileTypeGroup = findPreference("prefs_key_system_ui_statusbar_mobile_type_group");

        setMobileMode(mobileMode);
        mMobileMode.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        if (preference == mMobileMode) {
            setMobileMode(Integer.parseInt((String) o));
        }
        return true;
    }

    private void setMobileMode(int mode) {
        mMobileType.setEnabled(mode != 3);
        if (mode == 3) {
            mMobileTypeGroup.setVisible(false);
            mMobileType.setChecked(false);
            mMobileType.setSummary(R.string.system_ui_status_bar_mobile_type_single_desc);
        } else {
            mMobileType.setSummary("");
        }
    }
}
