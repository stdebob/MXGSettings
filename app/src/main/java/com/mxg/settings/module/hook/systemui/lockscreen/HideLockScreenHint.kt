// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.lockscreen

import android.widget.ImageView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectUtils
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.devicesdk.isAndroidVersion
import com.mxg.settings.utils.devicesdk.isMoreHyperOSVersion
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedHelpers

object HideLockScreenHint : BaseHook() {
    override fun init() {
        val hook: MethodHook = object : MethodHook() {
            @Throws(Throwable::class)
            override fun before(param: MethodHookParam) {
                XposedHelpers.setObjectField(param.thisObject, "mUpArrowIndication", null)
            }
        }

        if (isAndroidVersion(34) && isMoreHyperOSVersion(1f)) {
            // by Hyper Helper
            loadClassOrNull("com.android.systemui.statusbar.KeyguardIndicationController")!!.methodFinder()
                .filterByName("updateDeviceEntryIndication")
                .single().createHook {
                    after {
                        XposedHelpers.setObjectField(it.thisObject, "mPersistentUnlockMessage", "")
                    }
                }

            loadClassOrNull("com.android.systemui.statusbar.KeyguardIndicationController")!!.methodFinder()
                .filterByName("setIndicationArea")
                .single().createHook {
                    after {
                        val image =
                            ObjectUtils.getObjectOrNullAs<ImageView>(it.thisObject, "mUpArrow") ?: return@after
                        image.alpha = 0.0f
                    }
                }
        } else if (isAndroidVersion(33)) {
            findAndHookMethod(
                "com.android.systemui.keyguard.KeyguardIndicationRotateTextViewController",
                lpparam.classLoader,
                "hasIndicationsExceptResting",
                XC_MethodReplacement.returnConstant(true)
            )
        } else {
            findAndHookMethod(
                "com.android.systemui.statusbar.KeyguardIndicationController",
                lpparam.classLoader,
                "updateIndication",
                Boolean::class.javaPrimitiveType,
                Boolean::class.javaPrimitiveType,
                hook
            )
        }
    }
}
