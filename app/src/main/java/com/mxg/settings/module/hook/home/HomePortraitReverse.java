// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.mxg.settings.module.base.BaseHook;

public class HomePortraitReverse extends BaseHook {

    @Override
    public void init() {
        findAndHookMethod("com.miui.home.launcher.Launcher", "onCreate", Bundle.class, new MethodHook() {
            @Override
            protected void after(MethodHookParam param) throws Throwable {
                Activity act = (Activity) param.thisObject;
                act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            }
        });
    }
}
