// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.misettings

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

object ShowMoreFpsList : BaseHook() {
    override fun init() {
        loadClass("miui.util.FeatureParser").methodFinder()
            .filterByName("getIntArray")
            .single().createHook {
                before {
                    if (it.args[0] == "fpsList") {
                        it.result = intArrayOf(144, 120, 90, 60, 30)
                    }
                }
            }
    }
}
