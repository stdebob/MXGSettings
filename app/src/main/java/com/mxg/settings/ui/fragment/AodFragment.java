// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment;

import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;

import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;

import moralnorm.preference.SwitchPreference;

public class AodFragment extends SettingsPreferenceFragment {
    @Override
    public int getContentResId() {
        return R.xml.aod;
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity) getActivity()).showRestartDialog(
                getResources().getString(!isMoreHyperOSVersion(1f) ? R.string.aod : R.string.aod_hyperos),
                "com.miui.aod"
        );
    }

    @Override
    public void initPrefs() {
        SwitchPreference aod = findPreference("prefs_key_aod_unlock_always_on_display");
        aod.setVisible(!isMoreHyperOSVersion(1f));
        SwitchPreference aodH = findPreference("prefs_key_aod_unlock_always_on_display_hyper");
        aodH.setVisible(isMoreHyperOSVersion(1f));

    }
}
