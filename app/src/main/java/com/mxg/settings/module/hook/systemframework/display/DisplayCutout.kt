// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework.display

import com.mxg.settings.module.base.BaseHook

object DisplayCutout : BaseHook() {
    override fun init() {
        hookAllMethods("android.view.DisplayCutout", "pathAndDisplayCutoutFromSpec",
            object : MethodHook() {
                override fun before(param: MethodHookParam) {
                    param.args[0] = "M 0,0 H 0 V 0 Z"
                    param.args[1] = ""
                }
            }
        )
    }
}
