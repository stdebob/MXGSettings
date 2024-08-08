// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.title

import android.content.*
import com.mxg.settings.module.base.*
import com.mxg.settings.utils.devicesdk.*
import de.robv.android.xposed.*

object DisableHideFile : BaseHook() {
    override fun init() {
        if (isInternational()) return

        XposedHelpers.findAndHookConstructor(
            "com.miui.home.launcher.AppFilter",
            lpparam.classLoader,
            object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    val skippedItem = XposedHelpers.getObjectField(
                        param.thisObject,
                        "mSkippedItems"
                    ) as HashSet<ComponentName>

                    skippedItem.removeIf {
                        it.packageName == "com.google.android.documentsui"
                    }
                }
            }
        )
    }
}
