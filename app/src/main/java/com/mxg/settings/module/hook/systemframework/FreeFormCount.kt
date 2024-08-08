// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

class FreeFormCount : BaseHook() {
    override fun init() {
        val clazzMiuiFreeFormStackDisplayStrategy =
            loadClass("com.android.server.wm.MiuiFreeFormStackDisplayStrategy")
        // GetMaxMiuiFreeFormStackCount
        clazzMiuiFreeFormStackDisplayStrategy.methodFinder().filter {
            name in setOf(
                "getMaxMiuiFreeFormStackCount",
                "getMaxMiuiFreeFormStackCountForFlashBack"
            )
        }.toList().createHooks {
            returnConstant(256)
        }

        // ShouldStopStartFreeform
        clazzMiuiFreeFormStackDisplayStrategy.methodFinder()
            .filterByName("shouldStopStartFreeform")
            .single().createHook {
                returnConstant(false)
            }
    }
}
