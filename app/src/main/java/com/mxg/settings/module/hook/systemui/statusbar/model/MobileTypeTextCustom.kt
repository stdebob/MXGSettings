// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.statusbar.model

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

object MobileTypeTextCustom : BaseHook() {
    override fun init() {
        loadClass("com.android.systemui.statusbar.connectivity.MobileSignalController").methodFinder()
            .filterByName("getMobileTypeName")
            .filterByParamTypes {
                it[0] == Int::class.java
            }.single().createHook {
                after {
                    it.result = mPrefsMap.getString("system_ui_status_bar_mobile_type_custom", "ERR")
                }
            }
    }
}
