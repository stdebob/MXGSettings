// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.contentextension

import android.content.Context
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

class DoublePress : BaseHook() {
    override fun init() {
        loadClass("com.miui.contentextension.utils.ContentCatcherUtil").methodFinder()
            .filterByName("isCatcherSupportDoublePress")
            .filterByParamTypes {
                it[0] == Context::class.java
            }.single().createHook {
            returnConstant(true)
        }
    }
}
