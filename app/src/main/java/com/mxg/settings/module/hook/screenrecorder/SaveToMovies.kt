// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.screenrecorder

import com.mxg.settings.module.base.BaseHook
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import java.util.*

object SaveToMovies : BaseHook() {
    override fun init() {
        val clazz = XposedHelpers.findClass("android.os.Environment", lpparam.classLoader)
        XposedHelpers.setStaticObjectField(clazz, "DIRECTORY_DCIM", "Movies")

        XposedHelpers.findAndHookMethod("android.content.ContentValues",
            lpparam.classLoader,
            "put",
            String::class.java,
            String::class.java,
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    val param0 = param.args[0] as String
                    if (Objects.equals(param0, "relative_path")) {
                        param.args[1] = (param.args[1] as String).replace("DCIM", "Movies")
                    }
                }
            })
    }
}
