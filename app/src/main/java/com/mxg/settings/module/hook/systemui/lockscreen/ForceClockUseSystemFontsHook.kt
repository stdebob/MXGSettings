// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.lockscreen

import android.graphics.Typeface
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.getObjectFieldAs


object ForceClockUseSystemFontsHook : BaseHook() {
    override fun init() {
        loadClass("com.miui.clock.MiuiBaseClock").methodFinder().filter {
            name == "updateViewsTextSize"
        }.toList().createHooks {
            after { param ->
                val mTimeText =
                    param.thisObject.getObjectFieldAs<TextView>("mTimeText")
                mTimeText.typeface = Typeface.DEFAULT
            }
        }

        loadClass("com.miui.clock.MiuiLeftTopLargeClock").methodFinder().filter {
            name == "onLanguageChanged" && parameterTypes == String::class.java
        }.toList().createHooks {
            after { param ->
                val mTimeText =
                    param.thisObject.getObjectFieldAs<TextView>("mCurrentDateLarge")
                mTimeText.typeface = Typeface.DEFAULT
            }
        }
    }
}
