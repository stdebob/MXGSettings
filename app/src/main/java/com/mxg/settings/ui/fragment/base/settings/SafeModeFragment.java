// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.base.settings;

import static com.mxg.settings.utils.PropUtils.getProp;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;

import androidx.annotation.NonNull;

import com.mxg.settings.BuildConfig;
import com.mxg.settings.R;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;
import com.mxg.settings.utils.shell.ShellInit;

import moralnorm.preference.Preference;
import moralnorm.preference.SwitchPreference;

public class SafeModeFragment extends SettingsPreferenceFragment implements Preference.OnPreferenceChangeListener {

    String mPkgList = getProp("persist.hyperceiler.crash.report");

    SwitchPreference mHome;
    SwitchPreference mSettings;
    SwitchPreference mSystemUi;
    SwitchPreference mSecurityCenter;
    SwitchPreference mDemo;

    @Override
    public int getContentResId() {
        return R.xml.prefs_settings_safe_mode;
    }

    @Override
    public void initPrefs() {
        mHome = findPreference("prefs_key_home_safe_mode_enable");
        mSettings = findPreference("prefs_key_system_settings_state");
        mSystemUi = findPreference("prefs_key_system_ui_safe_mode_enable");
        mSecurityCenter = findPreference("prefs_key_security_center_safe_mode_enable");
        mDemo = findPreference("prefs_key_demo_safe_mode_enable");
        mSecurityCenter.setTitle(isMoreHyperOSVersion(1f) ? R.string.security_center_hyperos : R.string.security_center);
        mDemo.setVisible(BuildConfig.BUILD_TYPE == "debug");
        mSystemUi.setChecked(mPkgList.contains("systemui"));
        mSettings.setChecked(mPkgList.contains("settings"));
        mHome.setChecked(mPkgList.contains("home"));
        mSecurityCenter.setChecked(mPkgList.contains("center"));
        mDemo.setChecked(mPkgList.contains("demo"));
        mHome.setOnPreferenceChangeListener(this);
        mDemo.setOnPreferenceChangeListener(this);
        mSettings.setOnPreferenceChangeListener(this);
        mSystemUi.setOnPreferenceChangeListener(this);
        mSecurityCenter.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(@NonNull Preference preference, Object o) {
        String key = "";
        if (preference == mHome) {
            key = "home";
        } else if (preference == mSettings) {
            key = "settings";
        } else if (preference == mSystemUi) {
            key = "systemui";
        } else if (preference == mSecurityCenter) {
            key = "center";
        } else if (preference == mDemo) {
            key = "demo";
        }
        if (!key.isEmpty()) {
            String mPkgList = getProp("persist.hyperceiler.crash.report");
            if ((boolean) o) {
                if (mPkgList.isEmpty()) {
                    mPkgList = key;
                } else {
                    mPkgList += "," + key;
                }
            } else {
                mPkgList = mPkgList.replace("," + key, "").replace(key, "");
            }
            ShellInit.getShell().run("setprop persist.hyperceiler.crash.report \"" + mPkgList + "\"").sync();
        }
        return true;
    }

}
