// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemsettings

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

object UnlockTaplusForSettings : BaseHook() {
    override fun init() {
        loadClass("com.android.settings.utils.SettingsFeatures").methodFinder()
            .filterByName("isNeedRemoveContentExtension")
            .single().createHook {
                returnConstant(false)
            }
    }
}
