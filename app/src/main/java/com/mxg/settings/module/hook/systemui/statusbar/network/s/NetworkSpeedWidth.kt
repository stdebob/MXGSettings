// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.statusbar.network.s

import android.widget.TextView
import com.mxg.settings.module.base.BaseHook
import de.robv.android.xposed.XposedHelpers

object NetworkSpeedWidth : BaseHook() {
    override fun init() {
        // 固定宽度以防相邻元素左右防抖
        if (mPrefsMap.getInt("system_ui_statusbar_network_speed_fixedcontent_width", 10) > 10) {
            hookAllMethods(
                "com.android.systemui.statusbar.views.NetworkSpeedView",
                lpparam.classLoader,
                "applyNetworkSpeedState",
                object : MethodHook() {
                    override fun before(param: MethodHookParam) {
                        val meter = param.thisObject as TextView
                        if (meter.tag == null || "slot_text_icon" != meter.tag) {
                            XposedHelpers.getAdditionalInstanceField(param.thisObject, "inited")
                        }
                    }
                }
            )
        }
    }
}
