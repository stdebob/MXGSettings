// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.systemui;

import static com.mxg.settings.utils.devicesdk.MiDeviceAppUtilsKt.isPad;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMiuiVersion;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreAndroidVersion;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.view.View;

import androidx.annotation.NonNull;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;

import moralnorm.preference.DropDownPreference;
import moralnorm.preference.Preference;
import moralnorm.preference.PreferenceCategory;
import moralnorm.preference.SwitchPreference;

public class SystemUIOtherSettings extends SettingsPreferenceFragment {

    DropDownPreference mChargeAnimationStyle;
    PreferenceCategory mChargeAnimationTitle;
    SwitchPreference mMiuiMultiWinSwitch;
    SwitchPreference mMiuiMultiWinSwitchRemove;
    SwitchPreference mDisableInfinitymodeGesture;
    SwitchPreference mBottomBar;
    SwitchPreference mVolume;
    SwitchPreference mPower;
    SwitchPreference mDisableBluetoothRestrict; // 禁用蓝牙临时关闭
    SwitchPreference mPctUseBlur;

    @Override
    public int getContentResId() {
        return R.xml.system_ui_other;
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity) getActivity()).showRestartDialog(
                getResources().getString(R.string.system_ui),
                "com.android.systemui"
        );
    }

    @Override
    public void initPrefs() {
        mChargeAnimationStyle = findPreference("prefs_key_system_ui_charge_animation_style");
        mChargeAnimationTitle = findPreference("prefs_key_system_ui_statusbar_charge_animation_title");
        mDisableBluetoothRestrict = findPreference("prefs_key_system_ui_disable_bluetooth_restrict");
        mMiuiMultiWinSwitch = findPreference("prefs_key_system_ui_disable_miui_multi_win_switch");
        mMiuiMultiWinSwitchRemove = findPreference("prefs_key_system_ui_remove_miui_multi_win_switch");
        mDisableInfinitymodeGesture = findPreference("prefs_key_system_ui_disable_infinitymode_gesture");
        mBottomBar = findPreference("prefs_key_system_ui_disable_bottombar");
        mVolume = findPreference("prefs_key_system_ui_disable_volume");
        mPower = findPreference("prefs_key_system_ui_disable_power");
        mPctUseBlur = findPreference("prefs_key_system_showpct_use_blur");

        mChargeAnimationTitle.setVisible(!isMoreHyperOSVersion(1f));
        mDisableBluetoothRestrict.setVisible(isMiuiVersion(14f) && isMoreAndroidVersion(31));
        mMiuiMultiWinSwitch.setVisible(isMoreHyperOSVersion(1f) && isMoreAndroidVersion(34));
        mMiuiMultiWinSwitchRemove.setVisible(isMoreHyperOSVersion(1f) && isMoreAndroidVersion(34) && isPad());
        mDisableInfinitymodeGesture.setVisible(isMoreHyperOSVersion(1f) && isMoreAndroidVersion(34) && isPad());
        mBottomBar.setVisible(isMoreHyperOSVersion(1f) && isMoreAndroidVersion(34));
        mPctUseBlur.setVisible(isMoreHyperOSVersion(1f));

        mVolume.setOnPreferenceChangeListener(
                (preference, o) -> {
                    ComponentName componentName = new ComponentName("miui.systemui.plugin",
                            "miui.systemui.volume.VolumeDialogPlugin");
                    PackageManager packageManager = getContext().getPackageManager();
                    if ((boolean) o) {
                        packageManager.setComponentEnabledSetting(componentName,
                                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                                PackageManager.DONT_KILL_APP);
                    } else {
                        packageManager.setComponentEnabledSetting(componentName,
                                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                                PackageManager.DONT_KILL_APP);
                    }
                    return true;
                }
        );

        mPower.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object o) {
                ComponentName componentName = new ComponentName("miui.systemui.plugin",
                        "miui.systemui.globalactions.GlobalActionsPlugin");
                PackageManager packageManager = getContext().getPackageManager();
                if ((boolean) o) {
                    packageManager.setComponentEnabledSetting(componentName,
                            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                            PackageManager.DONT_KILL_APP);
                } else {
                    packageManager.setComponentEnabledSetting(componentName,
                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP);
                }
                return true;
            }
        });
    }
}
