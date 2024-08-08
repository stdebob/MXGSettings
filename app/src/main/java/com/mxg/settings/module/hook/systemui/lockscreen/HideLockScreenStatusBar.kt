// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.lockscreen

import android.view.*
import com.mxg.settings.module.base.*
import com.mxg.settings.utils.devicesdk.*
import de.robv.android.xposed.*

object HideLockScreenStatusBar : BaseHook() {
    override fun init() {
        if (isMoreAndroidVersion(34)) {
            hookAllMethods(
                "com.android.systemui.statusbar.phone.CentralSurfacesImpl", lpparam.classLoader,
                "updateIsKeyguard",
                object : MethodHook() {
                    override fun after(param: MethodHookParam) {
                        val shadeControllerImpl =
                            XposedHelpers.getObjectField(param.thisObject, "mShadeController")

                        val mKeyguardStatusBar = XposedHelpers.getObjectField(
                            XposedHelpers.getObjectField(
                                shadeControllerImpl,
                                "mNotificationPanelViewController"
                            ), "mKeyguardStatusBar"
                        ) as View
                        mKeyguardStatusBar.translationY = -999f
                    }
                }
            )
        } else {
            hookAllMethods(
                "com.android.systemui.statusbar.phone.CentralSurfacesImpl", lpparam.classLoader,
                "makeStatusBarView",
                object : MethodHook() {
                    override fun after(param: MethodHookParam) {
                        val mKeyguardStatusBar = XposedHelpers.getObjectField(
                            XposedHelpers.getObjectField(
                                param.thisObject,
                                "mNotificationPanelViewController"
                            ), "mKeyguardStatusBar"
                        ) as View
                        mKeyguardStatusBar.translationY = -999f
                    }
                }
            )
        }
    }
}
