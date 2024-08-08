// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.other;

import com.mxg.settings.module.base.BaseHook;

public class HomeMode extends BaseHook {

    Class<?> mDeviceConfig;

    @Override
    public void init() {

        mDeviceConfig = findClassIfExists("com.miui.home.launcher.DeviceConfig");

        findAndHookMethod(mDeviceConfig, "isDarkMode", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                int mHomeMode = mPrefsMap.getStringAsInt("home_other_home_mode", 0);
                boolean isHomeMode = (mHomeMode == 2);
                param.setResult(isHomeMode);
            }
        });
    }
}
