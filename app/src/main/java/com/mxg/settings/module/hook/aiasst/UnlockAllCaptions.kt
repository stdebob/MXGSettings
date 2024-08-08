// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.aiasst

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*

object UnlockAllCaptions : BaseHook() {
    override fun init() {
        // by PedroZ
        loadClass("com.xiaomi.aiasst.vision.common.BuildConfigUtils").methodFinder()
            .filterByName("isSupplierOnline")
            .single().createHook {
                returnConstant(true)
            }
    }
}
