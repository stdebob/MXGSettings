// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.other

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*

object AlwaysShowStatusClock : BaseHook() {
    private val mWorkspaceClass by lazy {
        loadClass("com.miui.home.launcher.Workspace")
    }

    override fun init() {
        mWorkspaceClass.methodFinder().filter {
            name in setOf("isScreenHasClockGadget", "isScreenHasClockWidget", "isClockWidget")
        }.toList().createHooks {
            returnConstant(false)
        }
    }
}
