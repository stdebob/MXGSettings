// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework.mipad

import com.github.kyuubiran.ezxhelper.ClassUtils.loadFirstClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

object IgnoreStylusKeyGesture : BaseHook() {
    override fun init() {
        val clazzMiuiStylusPageKeyListener = loadFirstClass(
            "com.miui.server.input.stylus.MiuiStylusPageKeyListener",
            "com.miui.server.stylus.MiuiStylusPageKeyListener"
        )
        val methodNames =
            setOf("isPageKeyEnable", "needInterceptBeforeDispatching", "shouldInterceptKey")

        clazzMiuiStylusPageKeyListener.methodFinder().filter {
            name in methodNames
        }.toList().createHooks {
            returnConstant(false)
        }
    }
}
