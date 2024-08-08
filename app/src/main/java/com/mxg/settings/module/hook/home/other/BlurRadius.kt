// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.other

import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.findClass
import com.mxg.settings.utils.hookBeforeAllMethods

object BlurRadius : BaseHook() {
    override fun init() {

        val value = mPrefsMap.getInt("home_other_blur_radius", 100).toFloat() / 100
        if (value == 1f) return
        val blurUtilsClass = "com.miui.home.launcher.common.BlurUtils".findClass()
        blurUtilsClass.hookBeforeAllMethods("fastBlur") {
            it.args[0] = it.args[0] as Float * value
        }

    }
}
