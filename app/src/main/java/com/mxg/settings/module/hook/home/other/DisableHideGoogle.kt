// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.other

import android.content.*
import com.mxg.settings.module.base.*
import com.mxg.settings.utils.*
import de.robv.android.xposed.*

@Suppress("UNCHECKED_CAST")
object DisableHideGoogle : BaseHook() {
    override fun init() {
        if (InvokeUtils.getStaticField("miui.os.Build", "IS_INTERNATIONAL_BUILD"))
            return

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
                        it.packageName == "com.google.android.googlequicksearchbox"
                            || it.packageName == "com.google.android.gms"
                    }
                }
            }
        )
    }

}
