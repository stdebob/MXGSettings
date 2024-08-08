// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.mediaeditor

import android.os.*
import com.github.kyuubiran.ezxhelper.ClassLoaderProvider.safeClassLoader
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.addUsingStringsEquals
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod
import com.mxg.settings.utils.api.LazyClass.AndroidBuildCls


object FilterManagerAll : BaseHook() {
    private lateinit var device: String
    private val methodResult by lazy {
        DexKit.getDexKitBridge("FilterManagerAll") { dexkit ->
            dexkit.findMethod {
                matcher {
                    addUsingStringsEquals("wayne")
                }
            }.filter { it.isMethod }.map { it.getMethodInstance(safeClassLoader) }.toTypedArray().firstOrNull()
        }.toMethod()
    }

    override fun init() {
        methodResult.createHook {
            before {
                if (!this@FilterManagerAll::device.isInitialized) {
                    device = Build.DEVICE
                }
                setStaticObject(AndroidBuildCls, "DEVICE", "wayne")
            }
            after {
                setStaticObject(AndroidBuildCls, "DEVICE", device)
            }
        }
    }
}
