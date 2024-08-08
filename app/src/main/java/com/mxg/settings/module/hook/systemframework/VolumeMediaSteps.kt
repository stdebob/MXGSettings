// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

class VolumeMediaSteps : BaseHook() {
    override fun init() {
        val mediaStepsSwitch = mPrefsMap.getInt("system_framework_volume_media_steps", 15) > 15
        val mediaSteps = mPrefsMap.getInt("system_framework_volume_media_steps", 15)

        loadClass("android.os.SystemProperties").methodFinder()
            .filterByName("getInt")
            .filterByReturnType(Int::class.java)
            .single().createHook {
                before {
                    when (it.args[0] as String) {
                        "ro.config.media_vol_steps" -> if (mediaStepsSwitch) it.result = mediaSteps
                    }
                }
            }

    }
}
