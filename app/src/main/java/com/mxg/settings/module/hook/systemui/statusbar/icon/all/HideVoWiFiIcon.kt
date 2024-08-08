// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.statusbar.icon.all

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.devicesdk.isAndroidVersion
import com.mxg.settings.utils.setBooleanField

object HideVoWiFiIcon : BaseHook() {
    override fun init() {
        val hideVoWifi by lazy {
            mPrefsMap.getBoolean("system_ui_status_bar_icon_vowifi")
        }
        val hideVolte by lazy {
            mPrefsMap.getBoolean("system_ui_status_bar_icon_volte")
        }
        if (isAndroidVersion(34)) {
            loadClass("com.android.systemui.MiuiOperatorCustomizedPolicy\$MiuiOperatorConfig").constructors[0].createHook {
                after {
                    it.thisObject.setBooleanField("hideVowifi", hideVoWifi)
                    it.thisObject.setBooleanField("hideVolte", hideVolte)
                }
            }
        } else if (hideVoWifi) {
            loadClass("com.android.systemui.MiuiOperatorCustomizedPolicy\$MiuiOperatorConfig").methodFinder()
                .filterByName("getHideVowifi")
                .single().createHook { returnConstant(true) }
        }
    }
}
