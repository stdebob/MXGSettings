// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.phone

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

object N5N8BandPhone : BaseHook() {
    override fun init() {
        runCatching {
            loadClass("miui.telephony.TelephonyManagerEx").methodFinder()
                .filterByName("isN5Supported")
                .single().createHook {
                    returnConstant(true)
                }
        }

        runCatching {
            loadClass("miui.telephony.TelephonyManagerEx").methodFinder()
                .filterByName("isN8Supported")
                .single().createHook {
                    returnConstant(true)
                }
        }
    }
}
