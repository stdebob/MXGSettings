// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework.display

import com.github.kyuubiran.ezxhelper.ClassUtils.invokeStaticMethodBestMatch
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*

object UseAOSPScreenShot : BaseHook() {
    override fun init() {
        // by WOMMO
        invokeStaticMethodBestMatch(
            loadClass("com.android.internal.util.ScreenshotHelperStub"), "getInstance"
        )?.let {
            it::class.java.methodFinder().filterByName("getServiceComponent").filterNonAbstract()
                .toList().createHooks {
                    returnConstant("com.android.systemui/com.android.systemui.screenshot.TakeScreenshotService")
                }
        }
    }
}
