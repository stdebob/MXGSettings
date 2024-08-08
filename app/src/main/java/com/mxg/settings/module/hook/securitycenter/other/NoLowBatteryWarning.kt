// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter.other

import android.provider.Settings
import com.mxg.settings.module.base.BaseHook

object NoLowBatteryWarning : BaseHook() {
    override fun init() {
        val settingHook: MethodHook = object : MethodHook() {
            override fun before(param: MethodHookParam) {
                val key = param.args[1] as String
                if ("low_battery_dialog_disabled" == key) param.result = 1
                else if ("low_battery_sound" == key) param.result = null
            }
        }
        hookAllMethods(Settings.System::class.java, "getInt", settingHook)
        hookAllMethods(Settings.Global::class.java, "getString", settingHook)
    }
}
