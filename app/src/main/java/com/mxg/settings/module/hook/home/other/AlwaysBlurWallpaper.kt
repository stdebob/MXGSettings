// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.other

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

object AlwaysBlurWallpaper : BaseHook() {
    private val value by lazy {
        mPrefsMap.getInt("home_blur_radius", 100)
    }

    override fun init() {
        loadClass("com.miui.home.launcher.common.BlurUtils").methodFinder()
            .filterByName("fastBlur")
            .filterByParamCount(4)
            .single().createHook {
                before {
                    it.args[0] = value.toFloat() / 100
                    it.args[2] = true
                }
            }
    }
}
