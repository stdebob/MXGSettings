// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemsettings

import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.api.LazyClass.SettingsFeaturesClass

class EnableFoldArea : BaseHook() {
    override fun init() {
        setStaticObject(
            SettingsFeaturesClass,
            "IS_SUPPORT_FOLD_SCREEN_SETTINGS",
            true
        )

        SettingsFeaturesClass.methodFinder()
            .filterByName("isSupportFoldScreenSettings")
            .single().createHook {
                returnConstant(true)
            }
    }
}
