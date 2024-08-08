// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app.SystemFramework.Phone;

import static com.mxg.settings.utils.devicesdk.MiDeviceAppUtilsKt.isPad;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreAndroidVersion;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.GlobalActions;
import com.mxg.settings.module.hook.systemframework.AllowAutoStart;
import com.mxg.settings.module.hook.systemframework.AllowDisableProtectedPackage;
import com.mxg.settings.module.hook.systemframework.AllowUntrustedTouch;
import com.mxg.settings.module.hook.systemframework.AllowUntrustedTouchForU;
import com.mxg.settings.module.hook.systemframework.AppLinkVerify;
import com.mxg.settings.module.hook.systemframework.BackgroundBlur;
import com.mxg.settings.module.hook.systemframework.CleanOpenMenu;
import com.mxg.settings.module.hook.systemframework.CleanShareMenu;
import com.mxg.settings.module.hook.systemframework.ClipboardWhitelist;
import com.mxg.settings.module.hook.systemframework.DeleteOnPostNotification;
import com.mxg.settings.module.hook.systemframework.DisableCleaner;
import com.mxg.settings.module.hook.systemframework.DisableGestureMonitor;
import com.mxg.settings.module.hook.systemframework.DisableFreeformBlackList;
import com.mxg.settings.module.hook.systemframework.DisableLowApiCheckForU;
import com.mxg.settings.module.hook.systemframework.DisableMiuiLite;
import com.mxg.settings.module.hook.systemframework.DisablePersistent;
import com.mxg.settings.module.hook.systemframework.DisablePinVerifyPer72h;
import com.mxg.settings.module.hook.systemframework.DisableVerifyCanBeDisabled;
import com.mxg.settings.module.hook.systemframework.FlagSecure;
import com.mxg.settings.module.hook.systemframework.FreeFormCount;
import com.mxg.settings.module.hook.systemframework.FreeformBubble;
import com.mxg.settings.module.hook.systemframework.HookEntry;
import com.mxg.settings.module.hook.systemframework.LinkTurboToast;
import com.mxg.settings.module.hook.systemframework.MultiFreeFormSupported;
import com.mxg.settings.module.hook.systemframework.PackagePermissions;
import com.mxg.settings.module.hook.systemframework.PstedClipboard;
import com.mxg.settings.module.hook.systemframework.QuickScreenshot;
import com.mxg.settings.module.hook.systemframework.RemoveSmallWindowRestrictions;
import com.mxg.settings.module.hook.systemframework.RotationButton;
import com.mxg.settings.module.hook.systemframework.ScreenRotation;
import com.mxg.settings.module.hook.systemframework.SpeedInstall;
import com.mxg.settings.module.hook.systemframework.StickyFloatingWindows;
import com.mxg.settings.module.hook.systemframework.SystemLockApp;
import com.mxg.settings.module.hook.systemframework.ThermalBrightness;
import com.mxg.settings.module.hook.systemframework.UseOriginalAnimation;
import com.mxg.settings.module.hook.systemframework.VolumeDefaultStream;
import com.mxg.settings.module.hook.systemframework.VolumeDisableSafe;
import com.mxg.settings.module.hook.systemframework.VolumeFirstPress;
import com.mxg.settings.module.hook.systemframework.VolumeMediaSteps;
import com.mxg.settings.module.hook.systemframework.VolumeSeparateControl;
import com.mxg.settings.module.hook.systemframework.VolumeSteps;
import com.mxg.settings.module.hook.systemframework.corepatch.BypassSignCheckForT;
import com.mxg.settings.module.hook.systemframework.display.AllDarkMode;
import com.mxg.settings.module.hook.systemframework.display.DisplayCutout;
import com.mxg.settings.module.hook.systemframework.display.ThemeProvider;
import com.mxg.settings.module.hook.systemframework.display.ToastTime;
import com.mxg.settings.module.hook.systemframework.display.UseAOSPScreenShot;
import com.mxg.settings.module.hook.systemframework.freeform.OpenAppInFreeForm;
import com.mxg.settings.module.hook.systemframework.freeform.UnForegroundPin;
import com.mxg.settings.module.hook.systemframework.mipad.IgnoreStylusKeyGesture;
import com.mxg.settings.module.hook.systemframework.mipad.NoMagicPointer;
import com.mxg.settings.module.hook.systemframework.mipad.RemoveStylusBluetoothRestriction;
import com.mxg.settings.module.hook.systemframework.mipad.RestoreEsc;
import com.mxg.settings.module.hook.systemframework.mipad.SetGestureNeedFingerNum;
import com.mxg.settings.module.hook.systemframework.network.DualNRSupport;
import com.mxg.settings.module.hook.systemframework.network.DualSASupport;
import com.mxg.settings.module.hook.systemframework.network.N1Band;
import com.mxg.settings.module.hook.systemframework.network.N28Band;
import com.mxg.settings.module.hook.systemframework.network.N5N8Band;
import com.mxg.settings.module.hook.various.NoAccessDeviceLogsRequest;

@HookExpand(pkg = "android", isPad = false, tarAndroid = 33)
public class SystemFrameworkT extends BaseModule {

    @Override
    public void handleLoadPackage() {
        // 小窗
        initHook(new AllowAutoStart(), mPrefsMap.getBoolean("system_framework_auto_start_apps_enable"));
        initHook(new FreeFormCount(), mPrefsMap.getBoolean("system_framework_freeform_count"));
        initHook(new FreeformBubble(), mPrefsMap.getBoolean("system_framework_freeform_bubble"));
        initHook(new DisableFreeformBlackList(), mPrefsMap.getBoolean("system_framework_disable_freeform_blacklist"));
        initHook(RemoveSmallWindowRestrictions.INSTANCE, mPrefsMap.getBoolean("system_framework_disable_freeform_blacklist"));
        initHook(new StickyFloatingWindows(), mPrefsMap.getBoolean("system_framework_freeform_sticky"));
        initHook(MultiFreeFormSupported.INSTANCE, mPrefsMap.getBoolean("system_framework_freeform_recents_to_small_freeform"));
        initHook(new OpenAppInFreeForm(), mPrefsMap.getBoolean("system_framework_freeform_jump"));
        initHook(new UnForegroundPin(), mPrefsMap.getBoolean("system_framework_freeform_foreground_pin"));
        // initHook(new OpenAppInFreeForm(), mPrefsMap.getBoolean("system_framework_freeform_jump"));

        // 音量
        initHook(new VolumeDefaultStream(), true);
        initHook(new VolumeFirstPress(), mPrefsMap.getBoolean("system_framework_volume_first_press"));
        initHook(new VolumeSeparateControl(), mPrefsMap.getBoolean("system_framework_volume_separate_control"));
        initHook(new VolumeSteps(), mPrefsMap.getInt("system_framework_volume_steps", 0) > 0);
        initHook(new VolumeMediaSteps(), mPrefsMap.getBoolean("system_framework_volume_media_steps_enable"));
        initHook(new VolumeDisableSafe(), mPrefsMap.getStringAsInt("system_framework_volume_disable_safe_new", 0) != 0);

        // 其他
        initHook(new SystemLockApp(), mPrefsMap.getBoolean("system_framework_guided_access"));
        initHook(new ScreenRotation(), mPrefsMap.getBoolean("system_framework_screen_all_rotations"));
        initHook(new CleanShareMenu(), mPrefsMap.getBoolean("system_framework_clean_share_menu"));
        initHook(new CleanOpenMenu(), mPrefsMap.getBoolean("system_framework_clean_open_menu"));
        initHook(new AllowUntrustedTouch(), mPrefsMap.getBoolean("system_framework_allow_untrusted_touch"));
        if (isMoreAndroidVersion(34))
            initHook(new AllowUntrustedTouchForU(), mPrefsMap.getBoolean("system_framework_allow_untrusted_touch"));
        initHook(new FlagSecure(), mPrefsMap.getBoolean("system_other_flag_secure"));
        initHook(new AppLinkVerify(), mPrefsMap.getBoolean("system_framework_disable_app_link_verify"));
        initHook(new UseOriginalAnimation(), mPrefsMap.getBoolean("system_framework_other_use_original_animation"));
        initHook(new SpeedInstall(), mPrefsMap.getBoolean("system_framework_other_speed_install"));
        initHook(DeleteOnPostNotification.INSTANCE, mPrefsMap.getBoolean("system_other_delete_on_post_notification"));
        initHook(NoAccessDeviceLogsRequest.INSTANCE, mPrefsMap.getBoolean("various_disable_access_device_logs"));
        initHook(new DisableMiuiLite(), mPrefsMap.getBoolean("system_framework_disablt_miuilite_check"));
        initHook(new HookEntry(), mPrefsMap.getBoolean("system_framework_hook_entry"));
        initHook(new PstedClipboard(), mPrefsMap.getBoolean("system_framework_posted_clipboard"));
        initHook(new AllowDisableProtectedPackage(), mPrefsMap.getBoolean("system_framework_allow_disable_protected_package"));
        // 允许应用后台读取剪切板
        initHook(new ClipboardWhitelist(), mPrefsMap.getBoolean("system_framework_clipboard_whitelist"));

        // 显示
        initHook(new BackgroundBlur(), mPrefsMap.getBoolean("system_framework_background_blur_supported"));
        initHook(DisplayCutout.INSTANCE, mPrefsMap.getBoolean("system_ui_display_hide_cutout_enable"));
        initHook(UseAOSPScreenShot.INSTANCE, mPrefsMap.getBoolean("system_ui_display_use_aosp_screenshot_enable"));
        initHook(new ToastTime(), mPrefsMap.getBoolean("system_ui_display_toast_times_enable"));
        initHook(new AllDarkMode(), mPrefsMap.getBoolean("system_framework_allow_all_dark_mode"));
        initHook(new ThemeProvider(), mPrefsMap.getBoolean("system_framework_allow_third_theme"));
        // initHook(new AutoBrightness(), mPrefsMap.getBoolean("system_control_center_auto_brightness"));

        // 位置模拟
        // initHook(new LocationSimulation(), false);

        // 小米/红米平板设置相关
        if (isPad()) {
            initHook(IgnoreStylusKeyGesture.INSTANCE, mPrefsMap.getBoolean("mipad_input_ingore_gesture"));
            initHook(NoMagicPointer.INSTANCE, mPrefsMap.getBoolean("mipad_input_close_magic"));
            initHook(RemoveStylusBluetoothRestriction.INSTANCE, mPrefsMap.getBoolean("mipad_input_disable_bluetooth_new"));
            initHook(RestoreEsc.INSTANCE, mPrefsMap.getBoolean("mipad_input_restore_esc"));
            initHook(SetGestureNeedFingerNum.INSTANCE, mPrefsMap.getBoolean("mipad_input_need_finger_num"));
        }

        // 核心破解
        initHook(BypassSignCheckForT.INSTANCE, mPrefsMap.getBoolean("system_framework_core_patch_auth_creak") || mPrefsMap.getBoolean("system_framework_core_patch_disable_integrity"));

        // 网络
        initHook(DualNRSupport.INSTANCE, mPrefsMap.getBoolean("phone_double_5g_nr"));
        initHook(DualSASupport.INSTANCE, mPrefsMap.getBoolean("phone_double_5g_sa"));
        initHook(N1Band.INSTANCE, mPrefsMap.getBoolean("phone_n1"));
        initHook(N5N8Band.INSTANCE, mPrefsMap.getBoolean("phone_n5_n8"));
        initHook(N28Band.INSTANCE, mPrefsMap.getBoolean("phone_n28"));

        // Other
        initHook(new PackagePermissions(), true);
        initHook(new RotationButton(), mPrefsMap.getStringAsInt("system_framework_other_rotation_button_int", 0) == 2);
        initHook(new GlobalActions(), mLoadPackageParam.processName.equals("android"));
        initHook(new ThermalBrightness(), mPrefsMap.getBoolean("system_framework_other_thermal_brightness"));
        initHook(DisableCleaner.INSTANCE, mPrefsMap.getBoolean("system_framework_other_disable_cleaner"));
        initHook(DisableGestureMonitor.INSTANCE, mPrefsMap.getBoolean("system_framework_other_disable_gesture_monitor"));
        initHook(new DisablePinVerifyPer72h(), mPrefsMap.getBoolean("system_framework_disable_72h_verify"));
        initHook(new DisableVerifyCanBeDisabled(), mPrefsMap.getBoolean("system_framework_disable_verify_can_ve_disabled"));
        initHook(new QuickScreenshot(), mPrefsMap.getBoolean("system_framework_quick_screenshot"));
        initHook(new LinkTurboToast(), mPrefsMap.getBoolean("system_framework_disable_link_turbo_toast"));

        initHook(new DisableLowApiCheckForU(), mPrefsMap.getBoolean("system_framework_disable_low_api_check") && isMoreAndroidVersion(34));
        initHook(new DisablePersistent(), mPrefsMap.getBoolean("system_framework_disable_persistent") && isMoreAndroidVersion(34));
    }

}
