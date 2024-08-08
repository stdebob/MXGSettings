// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.statusbar.icon.t

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

object UseNewHD : BaseHook() {
    // 仅供 Android 13 设备使用，部分未进版机型依旧不可用
    override fun init() {
        runCatching {
            loadClass("com.android.systemui.statusbar.policy.HDController").methodFinder()
                .filterByName("isVisible")
                .single().createHook {
                    returnConstant(true)
                }
        }
    }
}
