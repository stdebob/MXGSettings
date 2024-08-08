// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment;

import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreAndroidVersion;

import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;
import com.mxg.settings.utils.devicesdk.TelephonyManager;
import com.mxg.settings.utils.prefs.PrefsUtils;

import moralnorm.preference.Preference;
import moralnorm.preference.SwitchPreference;

public class SystemFrameworkFragment extends SettingsPreferenceFragment {
    SwitchPreference mDisableCreak;
    SwitchPreference mShareUser;
    SwitchPreference mDisableIntegrity;
    SwitchPreference mDisableLowApiCheck;
    SwitchPreference mDisablePersistent;
    Preference mNetwork;

    @Override
    public int getContentResId() {
        return R.xml.framework;
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity)getActivity()).showRestartSystemDialog();
    }

    @Override
    public void initPrefs() {
        boolean mCreak = PrefsUtils.getSharedBoolPrefs(getContext(), "prefs_key_system_framework_core_patch_auth_creak", false);
        mDisableCreak = findPreference("prefs_key_system_framework_core_patch_auth_creak");
        mShareUser = findPreference("prefs_key_system_framework_core_patch_shared_user");
        mDisableIntegrity = findPreference("prefs_key_system_framework_core_patch_disable_integrity");
        mDisableLowApiCheck = findPreference("prefs_key_system_framework_disable_low_api_check");
        mDisablePersistent = findPreference("prefs_key_system_framework_disable_persistent");
        mNetwork = findPreference("prefs_key_system_framework_network");

        mDisableIntegrity.setVisible(isMoreAndroidVersion(33) && !mCreak);
        mShareUser.setVisible(isMoreAndroidVersion(33)); // 暂时仅开放给 Android 13 及以上使用
        mNetwork.setVisible(TelephonyManager.getDefault().isFiveGCapable());
        mDisableLowApiCheck.setVisible(isMoreAndroidVersion(34));
        mDisablePersistent.setVisible(isMoreAndroidVersion(34));

        mDisableCreak.setOnPreferenceChangeListener((preference, o) -> {
            if ((boolean) o) {
                mDisableIntegrity.setChecked(false);
                mDisableIntegrity.setVisible(false);
            } else {
                mDisableIntegrity.setVisible(isMoreAndroidVersion(33));
            }
            return true;
        });
    }
}
