// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework.mipad

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

object NoMagicPointer : BaseHook() {
    override fun init() {
        loadClassOrNull("android.magicpointer.util.MiuiMagicPointerUtils")!!.methodFinder()
            .filterByName("isEnable")
            .single().createHook {
                returnConstant(false)
            }

        loadClass("com.android.server.SystemServerImpl").methodFinder()
            .filterByName("addMagicPointerManagerService")
            .single().createHook {
                returnConstant(null)
            }
    }
}
