// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.other

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

class OverlapMode : BaseHook() {
    override fun init() {
        // Fold2 样式负一屏
        loadClass("com.miui.home.launcher.overlay.assistant.AssistantDeviceAdapter").methodFinder()
            .filterByName("inOverlapMode")
            .single().createHook {
                returnConstant(true)
            }
    }
}
