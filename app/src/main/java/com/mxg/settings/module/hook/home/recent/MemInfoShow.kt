// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.recent

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

object MemInfoShow : BaseHook() {
    override fun init() {
        // hyperOS for Pad 修复方案来自 hyper helper
        try {
            // 此方法调用会将内存显示 hide，需拦截
            loadClass("com.miui.home.recents.views.RecentsDecorations").methodFinder()
                .filterByName("hideTxtMemoryInfoView")
                .single().createHook {
                returnConstant(null)
            }
        } catch (t: Throwable) {
            logE(TAG, "hideTxtMemoryInfoView method is null")
        }

        try {
            loadClass("com.miui.home.recents.views.RecentsDecorations").methodFinder()
                .filterByName("isMemInfoShow")
                .single()
        } catch (t: Throwable) {
            loadClass("com.miui.home.recents.views.RecentsDecorations").methodFinder()
                .filterByName("canTxtMemInfoShow")
                .single()
        }.createHook {
            returnConstant(true)
        }
    }
}
