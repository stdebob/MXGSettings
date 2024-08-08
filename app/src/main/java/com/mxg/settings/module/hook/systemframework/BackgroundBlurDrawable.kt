// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework

import android.graphics.Canvas
import com.mxg.settings.utils.log.XposedLogUtils.logI
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers

class BackgroundBlurDrawable : IXposedHookZygoteInit {
    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam) {
        val classLoader = startupParam.javaClass.classLoader
        val mBackgroundBlurDrawableClass = classLoader?.let {
            XposedHelpers.findClassIfExists(
                "com.android.internal.graphics.drawable.BackgroundBlurDrawable",
                it
            )
        } ?: return
        // 为 BackgroundBlurDrawable 应当增加一个判断
        // 此处应该可以为AOSP提交修复补丁
        XposedBridge.hookAllMethods(
            mBackgroundBlurDrawableClass,
            "draw",
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    val canvas = param.args[0] as Canvas
                    if (!canvas.isHardwareAccelerated) {
                        logI(
                            "BackgroundBlurDrawable",
                            "android",
                            "BackgroundBlurDrawable canvas is not HardwareAccelerated."
                        )
                        param.result = null
                    }
                }
            })
    }
}
