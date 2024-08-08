// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.layout;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XC_MethodReplacement;

public class UnlockGridsNoWord extends BaseHook {

    Class<?> mDeviceConfig;

    @Override
    public void init() {
        mDeviceConfig = findClassIfExists("com.miui.home.launcher.DeviceConfig");

        findAndHookMethod(mDeviceConfig, "isThemeCoverCellConfig", XC_MethodReplacement.returnConstant(true));
    }
}
