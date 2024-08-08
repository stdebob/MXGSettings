// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.dock

import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.hookBeforeMethod

object ShowDockIconTitle : BaseHook() {
    override fun init() {

        "com.miui.home.launcher.DeviceConfig".hookBeforeMethod("isHotseatsAppTitleHided") {
            it.result = false
        }

    }
}
