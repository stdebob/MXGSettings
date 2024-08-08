// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.dock

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*


object HideDock : BaseHook() {
    override fun init() {
        // 上滑时忽略dock,直接触发最近任务手势
        loadClass("com.miui.home.recents.GestureTouchEventTracker").methodFinder()
            .filterByName("isTouchCountAndHotSeatSupport").single().createHook {
                returnConstant(false)
            }

        // 拦截dock出现动画
        loadClass("com.miui.home.launcher.dock.DockStateMachine").methodFinder()
            .filterByName("transitionToAppearingState\$default").single().createHook {
                replace { }
            }
    }
}
