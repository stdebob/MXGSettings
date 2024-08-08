// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.layout;

import android.content.Context;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.utils.devicesdk.DisplayUtils;

public class HotSeatsHeight extends BaseHook {

    Class<?> mDeviceConfig;

    @Override
    public void init() {

        mDeviceConfig = findClassIfExists("com.miui.home.launcher.DeviceConfig");

        findAndHookMethod(mDeviceConfig, "calcHotSeatsHeight", Context.class, boolean.class, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                Context context = (Context) param.args[0];
                param.setResult(DisplayUtils.dp2px(context, mPrefsMap.getInt("home_layout_hotseats_height", 60)));
            }
        });
    }
}
