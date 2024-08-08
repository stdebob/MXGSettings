// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.recent

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.setObjectField

object DisableRecentViewWallpaperDarken : BaseHook() {
    override fun init() {
        loadClass("com.miui.home.recents.DimLayer").methodFinder()
            .filterByName("dim")
            .filterByParamCount(3)
            .single().createHook {
                before {
                    it.args[0] = 0.0f
                    it.thisObject.setObjectField("mCurrentAlpha", 0.0f)
                }
            }
    }
}
