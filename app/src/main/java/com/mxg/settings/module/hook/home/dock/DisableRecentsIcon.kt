// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.dock

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

object DisableRecentsIcon : BaseHook() {
    override fun init() {
        loadClass("com.miui.home.launcher.hotseats.HotSeatsListRecentsAppProvider").methodFinder()
            .filterByName("updateFinalRecommendTasks")
            .single().createHook {
            returnConstant(true)
        }
    }
}
