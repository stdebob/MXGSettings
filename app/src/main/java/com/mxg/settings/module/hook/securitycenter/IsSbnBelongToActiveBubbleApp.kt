// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

object IsSbnBelongToActiveBubbleApp : BaseHook() {
    override fun init() {
        runCatching {
            loadClass("com.miui.bubbles.settings.BubblesSettings").methodFinder()
                .filterByName("isSbnBelongToActiveBubbleApp")
                .single().createHook {
                    returnConstant(true)
                }
        }
    }
}
