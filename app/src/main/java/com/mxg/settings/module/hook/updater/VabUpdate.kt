// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.updater

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

class VabUpdate : BaseHook() {
    override fun init() {
        loadClass("miui.util.FeatureParser").methodFinder()
            .filterByName("hasFeature")
            .filterByParamCount(2)
            .single().createHook {
                before {
                    if (it.args[0] == "support_ota_validate") {
                        it.result = false
                    }
                }
            }
    }

}
