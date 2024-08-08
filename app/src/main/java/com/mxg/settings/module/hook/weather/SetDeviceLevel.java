// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.weather;

import android.content.Context;
import android.os.Bundle;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.utils.prefs.PrefsUtils;

import java.lang.reflect.Method;

public class SetDeviceLevel extends BaseHook {
    Class<?> mUtil = null;

    @Override
    public void init() {
        if ((mUtil = findClassIfExists("miuix.animation.utils.DeviceUtils")) == null) {
            // 看不懂
            mUtil = findClassIfExists("d7.a");
        }
        returnIntConstant(mUtil);
    }

    public static Bundle checkBundle(Context context, Bundle bundle) {
        if (context == null) {
            logE("SetWeatherDeviceLevel", "com.miui.weather2", "Context is null!");
            return null;
        }
        if (bundle == null) bundle = new Bundle();
        int order = Integer.parseInt(PrefsUtils.getSharedStringPrefs(context, "weather_device_level", "0"));
        bundle.putInt("current_sory_type", order - 1);
        bundle.putInt("current_sort_type", order - 1);
        return bundle;
    }

    private void returnIntConstant(Class<?> cls) {
        if (cls == null) {
            logE(TAG, "class is null");
            return;
        }
        int order = mPrefsMap.getStringAsInt("weather_device_level", 0);
        for (Method method : cls.getDeclaredMethods()) {
            if (method.getName().equals("transDeviceLevel")) {
                if (method.getReturnType().equals(int.class)
                    // && (method.getParameterTypes().length == 1 &&
                    //     method.getParameterTypes()[0].equals(int.class))
                ) {
                    hookMethod(method, MethodHook.returnConstant(order));
                }
            }
        }
        // if (getPackageVersionCode(lpparam) < 15000000)
        //     hookAllMethods(cls, "transDeviceLevel", MethodHook.returnConstant(order));
        // else
        //     findAndHookMethod(cls, "transDeviceLevel", int.class, XC_MethodReplacement.returnConstant(order));
    }
}
