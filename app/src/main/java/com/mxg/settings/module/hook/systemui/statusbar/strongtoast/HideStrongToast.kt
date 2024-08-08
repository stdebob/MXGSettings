// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.statusbar.strongtoast

import android.widget.FrameLayout
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.api.LazyClass.StrongToast


object HideStrongToast : BaseHook() {
    override fun init() {
        StrongToast!!.methodFinder()
            .filterByName("onAttachedToWindow")
            .single().createHook {
            after {
                val strongToastLayout = it.thisObject as FrameLayout
                strongToastLayout.viewTreeObserver.addOnPreDrawListener {
                    return@addOnPreDrawListener false
                }
            }
        }
    }
}
