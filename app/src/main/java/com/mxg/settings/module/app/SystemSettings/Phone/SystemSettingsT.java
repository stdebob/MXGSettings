/*
 * This file is part of HyperCeiler.

 * HyperCeiler is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.

 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.

 * Copyright (C) 2023-2024 HyperCeiler Contributions
 */
package com.mxg.settings.module.app.SystemSettings.Phone;

import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isAndroidVersion;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.systemsettings.AddGoogleListHeader;
import com.mxg.settings.module.hook.systemsettings.AddMiuiPlusEntry;
import com.mxg.settings.module.hook.systemsettings.AllowManageAllNotifications;
import com.mxg.settings.module.hook.systemsettings.AntiQues;
import com.mxg.settings.module.hook.systemsettings.AppsFreezerEnable;
import com.mxg.settings.module.hook.systemsettings.DisableInstallUnknownVerify;
import com.mxg.settings.module.hook.systemsettings.EnableFoldArea;
import com.mxg.settings.module.hook.systemsettings.EnablePadArea;
import com.mxg.settings.module.hook.systemsettings.EnableSpeedMode;
import com.mxg.settings.module.hook.systemsettings.HyperCeilerSettings;
import com.mxg.settings.module.hook.systemsettings.InternationalBuild;
import com.mxg.settings.module.hook.systemsettings.LanguageMenuShowAllApps;
import com.mxg.settings.module.hook.systemsettings.LinkTurbo;
import com.mxg.settings.module.hook.systemsettings.ModifySystemVersion;
import com.mxg.settings.module.hook.systemsettings.MoreNotificationSettings;
import com.mxg.settings.module.hook.systemsettings.NewNFCPage;
import com.mxg.settings.module.hook.systemsettings.NoveltyHaptic;
import com.mxg.settings.module.hook.systemsettings.QuickManageOverlayPermission;
import com.mxg.settings.module.hook.systemsettings.QuickManageUnknownAppSources;
import com.mxg.settings.module.hook.systemsettings.QuickManagerAccessibilityPermission;
import com.mxg.settings.module.hook.systemsettings.RunningServices;
import com.mxg.settings.module.hook.systemsettings.ShowAutoUIMode;
import com.mxg.settings.module.hook.systemsettings.UnLockAreaScreenshot;
import com.mxg.settings.module.hook.systemsettings.UnlockMaxFps;
import com.mxg.settings.module.hook.systemsettings.UnlockNeverSleepScreen;
import com.mxg.settings.module.hook.systemsettings.UnlockTaplusForSettings;
import com.mxg.settings.module.hook.systemsettings.UsbModeChoose;
import com.mxg.settings.module.hook.systemsettings.ViewWifiPasswordHook;
import com.mxg.settings.module.hook.systemsettings.VoipAssistantController;
import com.mxg.settings.module.hook.systemsettings.VolumeSeparateControlForSettings;
import com.mxg.settings.module.hook.systemsettings.aiimage.UnlockAi;
import com.mxg.settings.module.hook.systemsettings.aiimage.UnlockMemc;
import com.mxg.settings.module.hook.systemsettings.aiimage.UnlockSuperResolution;

@HookExpand(pkg = "com.android.settings", isPad = false, tarAndroid = 33)
public class SystemSettingsT extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new HyperCeilerSettings(), mPrefsMap.getStringAsInt("settings_icon", 0) != 0);

        initHook(new ShowAutoUIMode(), mPrefsMap.getBoolean("system_settings_unlock_ui_mode"));
        initHook(new LinkTurbo(), mPrefsMap.getBoolean("system_settings_linkturbo"));
        initHook(new RunningServices(), true); // 显示原生内存信息
        initHook(new UsbModeChoose(), mPrefsMap.getStringAsInt("system_settings_usb_mode_choose", 0) != 0
                || mPrefsMap.getBoolean("system_settings_usb_mode"));
        initHook(new ViewWifiPasswordHook(), mPrefsMap.getBoolean("system_settings_safe_wifi"));
        initHook(new VoipAssistantController(), mPrefsMap.getBoolean("system_settings_voip_assistant_controller"));
        initHook(new AddMiuiPlusEntry(), mPrefsMap.getBoolean("mirror_unlock_miui_plus"));
        initHook(new EnableSpeedMode(), mPrefsMap.getBoolean("system_settings_develop_speed_mode"));
        initHook(new QuickManageOverlayPermission(), mPrefsMap.getBoolean("system_settings_permission_show_app_up"));
        initHook(new QuickManageUnknownAppSources(), mPrefsMap.getBoolean("system_settings_permission_unknown_origin_app"));
        initHook(new QuickManagerAccessibilityPermission(), mPrefsMap.getBoolean("system_settings_permission_accessibility"));
        initHook(new InternationalBuild(), mPrefsMap.getBoolean("system_settings_international_build"));
        initHook(new DisableInstallUnknownVerify(), mPrefsMap.getBoolean("system_settings_permission_disable_install_unknown_verify"));
        initHook(new NewNFCPage(), mPrefsMap.getBoolean("system_settings_new_nfc_page"));
        initHook(new AppsFreezerEnable(), mPrefsMap.getBoolean("system_settings_apps_freezer"));
        // initHook(new BluetoothRestrict(), mPrefsMap.getBoolean("various_disable_bluetooth_restrict"));
        initHook(new VolumeSeparateControlForSettings(), mPrefsMap.getBoolean("system_framework_volume_separate_control") && !isMoreHyperOSVersion(1f));
        initHook(UnlockMaxFps.INSTANCE, mPrefsMap.getBoolean("system_settings_develop_max_fps"));
        initHook(new AntiQues(), mPrefsMap.getBoolean("system_settings_anti_ques"));

        initHook(new UnlockSuperResolution(), mPrefsMap.getBoolean("system_settings_ai_image_unlock_sr"));
        initHook(new UnlockAi(), mPrefsMap.getBoolean("system_settings_ai_image_unlock_ai"));
        initHook(new UnlockMemc(), mPrefsMap.getBoolean("system_settings_ai_image_unlock_memc"));
        initHook(UnLockAreaScreenshot.INSTANCE, mPrefsMap.getBoolean("system_settings_area_screenshot"));
        initHook(NoveltyHaptic.INSTANCE, mPrefsMap.getBoolean("system_settings_novelty_haptic"));
        initHook(new MoreNotificationSettings(), mPrefsMap.getBoolean("system_settings_more_notification_settings"));
        initHook(new AllowManageAllNotifications(), mPrefsMap.getBoolean("system_framework_allow_manage_all_notifications"));
        initHook(new LanguageMenuShowAllApps(), mPrefsMap.getBoolean("system_settings_lang_menu_shouw_all_app"));

        initHook(new EnablePadArea(), mPrefsMap.getBoolean("system_settings_enable_pad_area"));
        initHook(new EnableFoldArea(), mPrefsMap.getBoolean("system_settings_enable_fold_area"));

        initHook(new ModifySystemVersion(), mPrefsMap.getBoolean("updater_enable_miui_version") && mPrefsMap.getStringAsInt("updater_version_mode", 1) != 1);

        if (!isAndroidVersion(30)) {
            initHook(UnlockTaplusForSettings.INSTANCE, mPrefsMap.getBoolean("content_extension_unlock_taplus"));
        }

        initHook(new AddGoogleListHeader(), mPrefsMap.getBoolean("system_settings_unlock_google_header"));

        initHook(new UnlockNeverSleepScreen(), mPrefsMap.getBoolean("system_settings_allow_never_lock_screen"));

    }
}
