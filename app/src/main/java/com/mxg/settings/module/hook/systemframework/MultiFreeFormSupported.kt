// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook


object MultiFreeFormSupported : BaseHook() {
    override fun init() {
        runCatching {
            if (!mPrefsMap.getBoolean("system_framework_freeform_recents_to_small_freeform")) {
                loadClass("android.util.MiuiMultiWindowUtils").methodFinder()
                    .filterByName("multiFreeFormSupported")
                    .single().createHook {
                        before {
                            val ex = Throwable()
                            val stackTrace = ex.stackTrace
                            var mResult = true
                            for (i in stackTrace) {
                                if (i.className == "com.android.server.wm.MiuiFreeFormGestureController\$FreeFormReceiver") {
                                    mResult = false
                                    break
                                }
                            }
                            it.result = mResult
                        }
                    }
                logI(TAG, this.lpparam.packageName, "Hook with recents_to_small_freeform success!")
            } else {
                loadClass("android.util.MiuiMultiWindowUtils").methodFinder()
                    .filterByName("multiFreeFormSupported")
                    .single().createHook {
                        returnConstant(true)
                    }
                logI(TAG, this.lpparam.packageName, "Hook success!")
            }
        }
    }

}
