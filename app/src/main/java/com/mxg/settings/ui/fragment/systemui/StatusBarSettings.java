// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.systemui;

import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isHyperOSVersion;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreAndroidVersion;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;

import android.os.Bundle;
import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.prefs.RecommendPreference;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;

import moralnorm.preference.Preference;
import moralnorm.preference.PreferenceCategory;

public class StatusBarSettings extends SettingsPreferenceFragment {

    Preference mClockStatus; // 时钟指示器
    Preference mDeviceStatus; // 硬件指示器
    Preference mToastStatus; // 灵动 Toast
    Preference mIconManager;
    PreferenceCategory mStatusBarLayout; // 状态栏布局
    RecommendPreference mRecommend;

    @Override
    public int getContentResId() {
        return R.xml.system_ui_status_bar;
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
        mIconManager = findPreference("prefs_key_icon_manager");
        mClockStatus = findPreference("prefs_key_clock_status");

        mDeviceStatus = findPreference("prefs_key_system_ui_status_bar_device");
        mToastStatus = findPreference("prefs_key_system_ui_status_bar_toast");
        mStatusBarLayout = findPreference("pref_key_system_ui_statusbar_layout");
        mDeviceStatus.setVisible(!isHyperOSVersion(1f) || !isMoreAndroidVersion(34));
        mToastStatus.setVisible(isHyperOSVersion(1f));

        if (isMoreHyperOSVersion(1f)) {
            mIconManager.setFragment("com.mxg.settings.ui.fragment.systemui.statusbar.IconManageNewSettings");
            mClockStatus.setFragment("com.mxg.settings.ui.fragment.systemui.statusbar.NewClockIndicatorSettings");
        } else {
            mIconManager.setFragment("com.mxg.settings.ui.fragment.systemui.statusbar.IconManageSettings");
            mClockStatus.setFragment("com.mxg.settings.ui.fragment.systemui.statusbar.ClockIndicatorSettings");
        }

        mStatusBarLayout.setVisible(!isMoreHyperOSVersion(1f));

        Bundle args1 = new Bundle();
        mRecommend = new RecommendPreference(getContext());
        getPreferenceScreen().addPreference(mRecommend);

        args1.putString(":settings:fragment_args_key", "prefs_key_system_ui_lock_screen_hide_status_bar");
        mRecommend.addRecommendView(getString(R.string.system_ui_lock_screen_hide_status_bar),
                null,
                LockScreenSettings.class,
                args1,
                R.string.system_ui_lockscreen_title
        );

    }
}
