// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework;

import android.content.Context;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;

public class ScreenRotation extends BaseHook {

    @Override
    public void init() {

        findAndHookMethod("com.android.internal.view.RotationPolicy", "areAllRotationsAllowed", Context.class, XC_MethodReplacement.returnConstant(mPrefsMap.getBoolean("system_framework_screen_all_rotations")));

        hookAllConstructors("com.android.server.wm.DisplayRotation", new MethodHook() {
            @Override
            protected void after(MethodHookParam param) throws Throwable {
                XposedHelpers.setIntField(param.thisObject, "mAllowAllRotations", mPrefsMap.getBoolean("system_framework_screen_all_rotations") ? 1 : 0);
            }
        });
    }

    public static void initRes() {
        mResHook.setObjectReplacement("android", "bool", "config_allowAllRotations", mPrefsMap.getBoolean("system_framework_screen_all_rotations"));
    }
}
