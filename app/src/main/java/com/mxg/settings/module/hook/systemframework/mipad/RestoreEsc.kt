// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework.mipad

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

object RestoreEsc : BaseHook() {
    override fun init() {
        loadClass("com.android.server.input.config.InputCommonConfig").methodFinder()
            .filterByName("setPadMode")
            .single().createHook {
                before {
                    it.args[0] = false
                }
            }

        loadClass("com.android.server.input.InputManagerServiceStubImpl").methodFinder()
            .filterByName("switchPadMode")
            .single().createHook {
                before {
                    it.args[0] = false
                }
            }
    }
}
