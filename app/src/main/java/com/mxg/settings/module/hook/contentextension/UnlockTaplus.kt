// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.contentextension

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*
import com.mxg.settings.utils.api.LazyClass.clazzMiuiBuild

object UnlockTaplus : BaseHook() {
    override fun init() {
        loadClass("com.miui.contentextension.setting.activity.MainSettingsActivity").methodFinder()
            .filterByName("getFragment")
            .single().createHook {
                setStaticObject(
                    clazzMiuiBuild, "IS_TABLET", false
                )
            }
    }
}
