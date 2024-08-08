// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.aod

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

object UnlockAlwaysOnDisplay : BaseHook() {
    override fun init() {
        loadClass("com.miui.aod.widget.AODSettings").methodFinder()
            .filterByName("onlySupportKeycodeGoto")
            .single().createHook {
            returnConstant(false)
        }
    }
}
