// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.barrage

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

//from StarVoyager by @hosizoraru
object CustomBarrageLength : BaseHook() {
    private val barrageLength by lazy {
        mPrefsMap.getInt("barrage_length", 36)
    }

    override fun init() {
        val clazzString = loadClass("java.lang.String")

        clazzString.methodFinder()
            .filterByName("subSequence")
            .filterByParamCount(2)
            .first().createHook {
                before { param ->
                    if (Throwable().stackTrace.any { it.className == "com.xiaomi.barrage.utils.BarrageWindowUtils" }) {
                        param.args[1] = minOf(barrageLength, (param.thisObject as String).length)
                    }
                }

                after {
                    if (it.throwable != null) {
                        it.throwable = null
                        it.result = it.thisObject
                    }
                }
            }

        clazzString.methodFinder()
            .filterByName("length")
            .filterByParamCount(0)
            .first().createHook {
                after { param ->
                    val stacktrace = Throwable().stackTrace
                    if (stacktrace.any {
                            it.className in setOf(
                                "java.lang.String",
                                "android.text.SpannableStringBuilder"
                            )
                        }) return@after
                    if (stacktrace.any {
                            it.className == "com.xiaomi.barrage.utils.BarrageWindowUtils" && it.methodName in setOf(
                                "addBarrageNotification", "sendBarrage"
                            )
                        }) {
                        val realResult = (param.result as Int)
                        param.result = if (barrageLength < 36) {
                            if (realResult > barrageLength) {
                                maxOf(37, realResult)
                            } else realResult
                        } else {
                            if (realResult <= barrageLength) {
                                minOf(35, realResult)
                            } else realResult
                        }
                    }
                }
            }
    }
}
