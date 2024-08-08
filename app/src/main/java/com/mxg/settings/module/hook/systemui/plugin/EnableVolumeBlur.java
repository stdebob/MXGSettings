// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.plugin;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;

public class EnableVolumeBlur {
    public static void initEnableVolumeBlur(ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod("com.android.systemui.miui.volume.Util", classLoader, "isSupportBlurS", XC_MethodReplacement.returnConstant(true));
    }
}
