// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework

import com.mxg.settings.module.base.BaseHook

object DisableGestureMonitor : BaseHook() {
    override fun init() {
        hookAllMethods("com.miui.server.input.gesture.MiuiGestureMonitor", "registerPointerEventListener",
            object : MethodHook() {
                override fun before(param: MethodHookParam) {
                    param.result = null
                }
            }
        )
    }
}
