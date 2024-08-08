// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment;

import static com.hchen.hooktool.utils.SystemSDK.isPad;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;

import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;

import moralnorm.preference.Preference;

public class SecurityCenterFragment extends SettingsPreferenceFragment {

    Preference mPrivacy;

    @Override
    public int getContentResId() {
        return R.xml.security_center;
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity) getActivity()).showRestartDialog(
                isPad() ? getResources().getString(R.string.security_center_pad) : isMoreHyperOSVersion(1f) ? getResources().getString(R.string.security_center_hyperos) : getResources().getString(R.string.security_center),
                "com.miui.securitycenter"
        );
    }

    @Override
    public void initPrefs() {
        mPrivacy = findPreference("prefs_key_security_center_privacy_safety");
        // mPrivacy.setVisible(!isMoreHyperOSVersion(1f));
    }
}
