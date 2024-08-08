// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment;

import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;

import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;

import moralnorm.preference.SwitchPreference;

public class MiLinkFragment extends SettingsPreferenceFragment {

    SwitchPreference mUnlockHMind;

    @Override
    public int getContentResId() {
        return R.xml.milink;
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity)getActivity()).showRestartDialog(
            getResources().getString(!isMoreHyperOSVersion(1f) ? R.string.milink : R.string.milink_hyperos),
            "com.milink.service"
        );
    }

    @Override
    public void initPrefs() {
        mUnlockHMind = findPreference("prefs_key_milink_unlock_hmind");
        if (mUnlockHMind != null) {
            mUnlockHMind.setVisible(isMoreHyperOSVersion(1f));
        }
    }
}
