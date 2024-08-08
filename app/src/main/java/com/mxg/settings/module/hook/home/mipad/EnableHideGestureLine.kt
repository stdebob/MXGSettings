// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.mipad

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

object EnableHideGestureLine : BaseHook() {
    override fun init() {
        loadClass("com.miui.home.recents.settings.NavigationBarTypePreferenceFragment").methodFinder().first{
            name == "updatePreferenceVisibility"
        }.createHook{
            before{
                it.result = true
            }
        }


        loadClass("com.miui.home.recents.BaseRecentsImpl").methodFinder().first{
            name == "initHideGestureLine"
        }.createHook{
            before{
                it.result = null
            }
        }
    }
}
