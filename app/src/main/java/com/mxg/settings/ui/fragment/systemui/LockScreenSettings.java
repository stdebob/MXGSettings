// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.systemui;

import static com.mxg.settings.utils.api.OldFunApisKt.isDeviceEncrypted;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isHyperOSVersion;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreAndroidVersion;

import android.os.Build;
import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;

import moralnorm.preference.SwitchPreference;

public class LockScreenSettings extends SettingsPreferenceFragment {
    SwitchPreference mShowSec; // 时钟显示秒数
    SwitchPreference mForceSystemFonts; // 时钟使用系统字体
    SwitchPreference mPasswordFree; // 开机免输入密码
    SwitchPreference mChangingCVTime; // 充电信息显示刷新间隔

    @Override
    public int getContentResId() {
        return R.xml.system_ui_lock_screen;
    }

    @Override
    public void initPrefs() {
        mForceSystemFonts = findPreference("prefs_key_system_ui_lock_screen_force_system_fonts");
        mPasswordFree = findPreference("prefs_key_system_ui_lock_screen_password_free");
        mChangingCVTime = findPreference("prefs_key_system_ui_lock_screen_show_spacing_value");
        mShowSec = findPreference("prefs_key_system_ui_lock_screen_show_second");

        mShowSec.setVisible(!isHyperOSVersion(1f));
        mForceSystemFonts.setVisible(!isHyperOSVersion(1f));
        mChangingCVTime.setVisible(isMoreAndroidVersion(Build.VERSION_CODES.TIRAMISU));

        if (isDeviceEncrypted(requireContext())) {
            mPasswordFree.setChecked(false);
            mPasswordFree.setEnabled(false);
            mPasswordFree.setSummary(R.string.system_ui_lock_screen_password_free_tip);
        }
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity)getActivity()).showRestartDialog(
            getResources().getString(R.string.system_ui),
            "com.android.systemui"
        );
    }
}
