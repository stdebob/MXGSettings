// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.phone

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

object ModemFeature : BaseHook() {
    override fun init() {
        runCatching {
            loadClass("com.android.phone.FiveGManagerBase").methodFinder()
                .filterByName("getModemFeatureMode")
                .single().createHook {
                    after {
                        it.args[0] = -1
                        it.result = true
                    }
                }
        }

        runCatching {
            loadClass("com.android.phone.MiuiPhoneUtils").methodFinder()
                .filterByName("isModemFeatureSupported")
                .single().createHook {
                    after {
                        it.args[0] = -1
                    }
                }
        }

        runCatching {
            loadClass("com.android.phone.MiuiPhoneUtils").methodFinder()
                .filterByName("getModemFeatureFromDb")
                .single().createHook {
                    after {
                        it.args[0] = -1
                    }
                }
        }
    }
}
