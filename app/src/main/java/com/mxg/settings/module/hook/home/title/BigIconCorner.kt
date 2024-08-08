// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.title

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.getObjectField

object BigIconCorner : BaseHook() {
    private val maMlHostViewClass by lazy {
        loadClass("com.miui.home.launcher.maml.MaMlHostView")
    }

    override fun init() {
        loadClass("com.miui.home.launcher.bigicon.BigIconUtil").methodFinder().filter {
            name == "getCroppedFromCorner" && parameterCount == 4
        }.toList().createHooks {
            before {
                it.args[0] = 2
                it.args[1] = 2
            }
        }

        maMlHostViewClass.methodFinder()
            .filterByName("getCornerRadius")
            .single().createHook {
                before {
                    it.result = it.thisObject.getObjectField("mEnforcedCornerRadius") as Float
                }
            }

        maMlHostViewClass.methodFinder()
            .filterByName("computeRoundedCornerRadius")
            .filterByParamCount(1)
            .single().createHook {
                before {
                    it.result = it.thisObject.getObjectField("mEnforcedCornerRadius") as Float
                }
            }

        loadClass("com.miui.home.launcher.LauncherAppWidgetHostView").methodFinder()
            .filterByName("computeRoundedCornerRadius")
            .filterByParamCount(1)
            .single().createHook {
                before {
                    it.result = it.thisObject.getObjectField("mEnforcedCornerRadius") as Float
                }
            }
    }
}
