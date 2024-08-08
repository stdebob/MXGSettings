// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.screenrecorder

import com.mxg.settings.module.base.BaseHook
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import java.util.*

class ForceSupportPlaybackCapture : BaseHook() {
    override fun init() {
        // if (!xPrefs.getBoolean("force_support_playbackcapture", true)) return

        XposedHelpers.findAndHookMethod("android.os.SystemProperties",
            lpparam.classLoader,
            "getBoolean",
            String::class.java,
            Boolean::class.javaPrimitiveType,
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    val param0 = param.args[0] as String
                    if (Objects.equals(param0, "ro.vendor.audio.playbackcapture.screen"))
                        param.result = true
                }
            })
    }
}
