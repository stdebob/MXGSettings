// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter.other

import android.view.*
import com.github.kyuubiran.ezxhelper.ClassLoaderProvider.safeClassLoader
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod
import org.luckypray.dexkit.query.enums.*

object LockOneHundredPoints : BaseHook() {
    private val score by lazy {
        DexKit.getDexKitBridge("LockOneHundredPoints1") {
            it.findMethod {
                matcher {
                    declaredClass {
                        addUsingString("getMinusPredictScore", StringMatchType.Contains)
                    }
                    usingNumbers(41, 100, 0)
                    returnType = "int"
                }
            }.single().getMethodInstance(safeClassLoader)
        }.toMethod()
    }

    private val scoreOld by lazy {
        DexKit.getDexKitBridge("LockOneHundredPoints2") {
            it.findMethod {
                matcher {
                    addUsingString("getMinusPredictScore", StringMatchType.Contains)
                }
            }.single().getMethodInstance(safeClassLoader)
        }.toMethod()
    }

    override fun init() {
        loadClass("com.miui.securityscan.ui.main.MainContentFrame").methodFinder()
            .filterByName("onClick")
            .filterByParamTypes(View::class.java)
            .first().createHook {
               returnConstant(null)
            }

        try {
            logI(TAG, lpparam.packageName, "LockOneHundredPoints method is $score")
            score.createHook {
                replace { 100 }
            }
        } catch (e: Exception) {
            logE(TAG, lpparam.packageName, "LockOneHundredPoints hook Failed: ${e.message}")
            logI(TAG, lpparam.packageName, "LockOneHundredPoints old method is $scoreOld")
            scoreOld.createHook {
                replace { 0 }
            }
        }
    }
}
