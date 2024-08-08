// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment;

import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;
import com.mxg.settings.utils.shell.ShellInit;

import moralnorm.preference.Preference;

public class PhoneFragment extends SettingsPreferenceFragment {
    Preference mPhone;

    @Override
    public int getContentResId() {
        return R.xml.phone;
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity) getActivity()).showRestartDialog(
            getResources().getString(R.string.phone),
            "com.android.phone"
        );
    }

    @Override
    public void initPrefs() {
        mPhone = findPreference("prefs_key_phone_additional_network_settings");
        mPhone.setOnPreferenceClickListener(
            preference -> {
                ShellInit.getShell().run("am start -n com.android.phone/.SwitchDebugActivity").sync();
                return true;
            }
        );
    }
}
