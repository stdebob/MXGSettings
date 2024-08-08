// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.statusbar.network.old

import android.view.View
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import de.robv.android.xposed.XposedHelpers


object StatusBarNoNetSpeedSep : BaseHook() {
    override fun init() {
        loadClass("com.android.systemui.statusbar.views.NetworkSpeedSplitter", lpparam.classLoader).methodFinder()
            .filterByName("updateVisibility")
            .single().createHook {
                before {
                    XposedHelpers.setObjectField(it.thisObject, "mNetworkSpeedVisibility", View.GONE)
                }
            }
    }
}
