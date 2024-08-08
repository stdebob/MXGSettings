// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.packageinstaller

import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.addUsingStringsEquals
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod
import com.mxg.settings.utils.*

object DisableSafeModelTip : BaseHook() {
    override fun init() {
        DexKit.getDexKitBridge("DisableSafeModelTip") {
            it.findMethod {
                matcher {
                    addUsingStringsEquals("android.provider.MiuiSettings\$Ad")
                }
            }.firstOrNull()?.getMethodInstance(lpparam.classLoader)
        }.toMethod().createHook {
            returnConstant(false)
        }

        // 屏蔽每 30 天提示开启安全守护的弹窗（已知问题：完成和打开按钮无反应）
        /*val result2 = Objects.requireNonNull(
            mPackageInstallerResultMethodsMap!!["Disable30DaysDialog"]
        )

        for (descriptor in result2) {
            val mDisableSafeModelTip = descriptor.getMethodInstance(lpparam.classLoader)
            mDisableSafeModelTip.createHook {
                returnConstant(null)
            }
        }*/

        var letter = 'a'
        for (i in 0..25) {
            try {
                val classIfExists =
                    "com.miui.packageInstaller.ui.listcomponets.${letter}0".findClassOrNull()
                classIfExists?.let {
                    it.methodFinder().filterByName("a").first().createHook {
                        after { hookParam ->
                            try {
                                hookParam.thisObject.setBooleanField("m", false)
                            } catch (_: Throwable) {
                                hookParam.thisObject.setBooleanField("l", false)
                            }
                        }
                    }
                }
            } catch (t: Throwable) {
                letter++
            }
        }
    }
}
