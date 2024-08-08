// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.statusbar.icon.all

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

object StatusBarSimIcon : BaseHook() {
    private val card1 by lazy {
        mPrefsMap.getBoolean("system_ui_status_bar_icon_mobile_network_hide_card_1")
    }
    private val card2 by lazy {
        mPrefsMap.getBoolean("system_ui_status_bar_icon_mobile_network_hide_card_2")
    }

    override fun init() {
        loadClass("com.android.systemui.statusbar.phone.StatusBarSignalPolicy").methodFinder()
            .filterByName("hasCorrectSubs")
            .filterByParamTypes {
                it[0] == MutableList::class.java
            }.single().createHook {
                before {
                    val list = it.args[0] as MutableList<*>
                    /* val size = list.size*/
                    if (card2) {
                        list.removeAt(1)
                    }
                    if (card1) {
                        list.removeAt(0)
                    }
                }
            }
    }

}
