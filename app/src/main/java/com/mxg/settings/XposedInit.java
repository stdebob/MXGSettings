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
package com.mxg.settings;

import com.github.kyuubiran.ezxhelper.EzXHelper;
import com.mxg.settings.module.base.BaseXposedInit;
import com.mxg.settings.module.hook.systemframework.AllowManageAllNotifications;
import com.mxg.settings.module.hook.systemframework.AllowUninstall;
import com.mxg.settings.module.hook.systemframework.BackgroundBlurDrawable;
import com.mxg.settings.module.hook.systemframework.CleanOpenMenu;
import com.mxg.settings.module.hook.systemframework.CleanShareMenu;
import com.mxg.settings.module.hook.systemframework.ScreenRotation;
import com.mxg.settings.module.hook.systemframework.ToastBlur;
import com.mxg.settings.module.hook.systemframework.UnlockAlwaysOnDisplay;
import com.mxg.settings.module.hook.systemframework.network.FlightModeHotSpot;
import com.mxg.settings.module.hook.systemsettings.VolumeSeparateControlForSettings;
import com.mxg.settings.module.skip.SystemFrameworkForCorePatch;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XposedInit extends BaseXposedInit implements IXposedHookZygoteInit, IXposedHookLoadPackage {
    private final String TAG = "HyperCeiler";

    @Override
    public void initZygote(IXposedHookZygoteInit.StartupParam startupParam) throws Throwable {
        super.initZygote(startupParam);
        EzXHelper.initZygote(startupParam);
        EzXHelper.setLogTag(TAG);
        EzXHelper.setToastTag(TAG);
        if (mPrefsMap.getBoolean("system_framework_allow_uninstall"))
            new AllowUninstall().initZygote(startupParam);
        if (mPrefsMap.getBoolean("system_framework_screen_all_rotations")) ScreenRotation.initRes();
        if (mPrefsMap.getBoolean("system_framework_clean_share_menu")) CleanShareMenu.initRes();
        if (mPrefsMap.getBoolean("system_framework_clean_open_menu")) CleanOpenMenu.initRes();
        if (mPrefsMap.getBoolean("system_framework_volume_separate_control"))
            VolumeSeparateControlForSettings.initRes();
        if (mPrefsMap.getBoolean("system_framework_allow_manage_all_notifications"))
            new AllowManageAllNotifications().initZygote(startupParam);
        if (startupParam != null) {
            new BackgroundBlurDrawable().initZygote(startupParam);
            new SystemFrameworkForCorePatch().initZygote(startupParam);
        }
        if (mPrefsMap.getBoolean("system_framework_background_blur_toast"))
            new ToastBlur().initZygote(startupParam);
        if (mPrefsMap.getBoolean("aod_unlock_always_on_display_hyper"))
            new UnlockAlwaysOnDisplay().initZygote(startupParam);
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if ("com.miui.contentcatcher".equals(lpparam.packageName) ||
                "com.miui.catcherpatch".equals(lpparam.packageName)) {
            return;
        }
        init(lpparam);
        new SystemFrameworkForCorePatch().handleLoadPackage(lpparam);
        if (mPrefsMap.getBoolean("system_framework_network_flightmode_hotspot"))
            new FlightModeHotSpot().handleLoadPackage(lpparam);
    }
}
