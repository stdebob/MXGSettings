// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

object DisableChargeAnimation : BaseHook() {
    override fun init() {
        /*loadClass("com.miui.charge.ChargeUtils", lpparam.classLoader).methodFinder().first {
            name == "getChargeAnimationType"
        }.createHook {
            returnConstant(4)
            // 0, 4以上: 光韵
            // 1: 流光
            // 2: 流光 Pad
            // 3: 光韵渐入
        }*/

        loadClass("com.miui.charge.container.MiuiChargeContainerView").methodFinder()
            .filterByName("init")
            .single().createHook {
                returnConstant(null)
            }
    }
}
