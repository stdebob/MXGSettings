// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.recent

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

object HideStatusBarWhenEnterRecent : BaseHook() {
    override fun init() {
        // 不应该在默认情况下强制显示
        // if (mPrefsMap.getBoolean("home_recent_hide_status_bar_in_task_view")) {
        loadClass("com.miui.home.launcher.common.DeviceLevelUtils").methodFinder()
            .filterByName("isHideStatusBarWhenEnterRecents")
            .single().createHook {
            returnConstant(true)
        }

        loadClass("com.miui.home.launcher.DeviceConfig").methodFinder()
            .filterByName("keepStatusBarShowingForBetterPerformance")
            .single().createHook {
            returnConstant(false)
        }
        // } else {
        //     mDeviceLevelClass.methodFinder().first {
        //         name == "isHideStatusBarWhenEnterRecents"
        //     }.createHook {
        //         returnConstant(false)
        //     }
        // }
    }
}
