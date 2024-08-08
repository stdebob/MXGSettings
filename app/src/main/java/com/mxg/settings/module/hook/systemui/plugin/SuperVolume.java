// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.plugin;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;

public class SuperVolume {
    // Referenced from StarVoyager
    public static void initSuperVolume(ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod("miui.systemui.util.CommonUtils", classLoader, "supportSuperVolume", XC_MethodReplacement.returnConstant(true));
        XposedHelpers.findAndHookMethod("miui.systemui.util.CommonUtils", classLoader, "voiceSupportSuperVolume", XC_MethodReplacement.returnConstant(true));
    }
}
