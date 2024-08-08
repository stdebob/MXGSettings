// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemsettings

import com.mxg.settings.module.base.BaseHook

object UnLockAreaScreenshot : BaseHook() {
    override fun init() {
        findAndHookMethod(
            "com.android.settings.MiuiShortcut\$System", "supportPartialScreenShot",
            object : MethodHook() {
                override fun before(param: MethodHookParam?) {
                    param?.result = true
                }
            })
    }
}
