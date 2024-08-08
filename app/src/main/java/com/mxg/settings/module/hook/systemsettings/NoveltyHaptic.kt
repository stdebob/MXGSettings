// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemsettings

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

object NoveltyHaptic : BaseHook() {
    override fun init() {
        if (mPrefsMap.getBoolean("system_settings_international_build")) return // 开启国际版设置界面将禁用此功能
        loadClass("com.android.settings.utils.SettingsFeatures").methodFinder()
            .filterByName("isNoveltyHaptic")
            .single().createHook {
                returnConstant(true)
            }
    }
}
