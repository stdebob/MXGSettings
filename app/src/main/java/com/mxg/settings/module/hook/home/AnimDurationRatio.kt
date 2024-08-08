// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

object AnimDurationRatio : BaseHook() {
    override fun init() {
        var value1 = mPrefsMap.getInt("home_title_animation_speed", 100).toFloat()
        var value2 = mPrefsMap.getInt("home_recent_animation_speed", 100).toFloat()
        if (value1 != 100f) {
            value1 /= 100f
            loadClass("com.miui.home.recents.util.RectFSpringAnim").methodFinder()
                .filterByName("getModifyResponse")
                .single().createHook {
                    before {
                        it.result = it.args[0] as Float * value1
                    }
                }
        }
        if (value2 != 100f) {
            value2 /= 100f
            loadClass("com.miui.home.launcher.common.DeviceLevelUtils").methodFinder()
                .filterByName("getDeviceLevelTransitionAnimRatio")
                .single().createHook {
                    before {
                        it.result = value2
                    }
                }
        }
    }
}
