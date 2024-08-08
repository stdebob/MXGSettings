// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.widget

import android.view.View
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.callMethod
import com.mxg.settings.utils.hookAfterMethod
import java.util.function.Predicate

object HideWidgetTitles : BaseHook() {
    override fun init() {

        val maMlWidgetInfo = loadClass("com.miui.home.launcher.maml.MaMlWidgetInfo")
        loadClass("com.miui.home.launcher.LauncherAppWidgetHost").methodFinder()
            .filterByName("createLauncherWidgetView")
            .filterByParamCount(4)
            .single().createHook {
                after {
                    val view = it.result as Any
                    view.callMethod("getTitleView")?.callMethod("setVisibility", View.GONE)
                }
            }

        "com.miui.home.launcher.Launcher".hookAfterMethod(
            "addMaMl", maMlWidgetInfo, Boolean::class.java, Predicate::class.java
        ) {
            val view = it.result as Any
            view.callMethod("getTitleView")?.callMethod("setVisibility", View.GONE)
        }

    }
}
