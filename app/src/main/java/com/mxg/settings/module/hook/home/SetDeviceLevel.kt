// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.api.LazyClass.SystemProperties
import com.mxg.settings.utils.findClass
import com.mxg.settings.utils.hookBeforeMethod
import com.mxg.settings.utils.replaceMethod

object SetDeviceLevel : BaseHook() {
    private val mDeviceLevelUtilsClass by lazy {
        loadClass("com.miui.home.launcher.common.DeviceLevelUtils")
    }
    private val mDeviceConfigClass by lazy {
        loadClass("com.miui.home.launcher.DeviceConfig")
    }

    override fun init() {
        try {
            loadClass("com.miui.home.launcher.common.CpuLevelUtils").methodFinder()
                .filterByName("getQualcommCpuLevel")
                .filterByParamCount(1)
                .single()
        } catch (e: Exception) {
            loadClass("miuix.animation.utils.DeviceUtils").methodFinder()
                .filterByName("getQualcommCpuLevel")
                .filterByParamCount(1)
                .single()
        }.createHook {
            returnConstant(2)
        }

        runCatching {
            mDeviceConfigClass.methodFinder()
                .filterByName("isUseSimpleAnim")
                .single().createHook {
                    returnConstant(false)
            }
        }
        runCatching {
            mDeviceLevelUtilsClass.methodFinder()
                .filterByName("getDeviceLevel")
                .single().createHook {
                    returnConstant(2)
            }
        }
        runCatching {
            mDeviceConfigClass.methodFinder()
                .filterByName("isSupportCompleteAnimation")
                .single().createHook {
                    returnConstant(true)
            }
        }
        runCatching {
            mDeviceLevelUtilsClass.methodFinder()
                .filterByName("isLowLevelOrLiteDevice")
                .single().createHook {
                    returnConstant(false)
            }
        }
        runCatching {
            mDeviceConfigClass.methodFinder()
                .filterByName("isMiuiLiteVersion")
                .single().createHook {
                    returnConstant(false)
            }
        }
        runCatching {
            "com.miui.home.launcher.util.noword.NoWordSettingHelperKt".hookBeforeMethod(
                "isNoWordAvailable"
            ) { it.result = true }
        }

        runCatching {
            SystemProperties.methodFinder().filter {
                name == "getBoolean" && parameterTypes[0] == String::class.java && parameterTypes[1] == Boolean::class.java
            }.toList().createHooks {
                before {
                    if (it.args[0] == "ro.config.low_ram.threshold_gb") it.result = false
                    if (it.args[0] == "ro.miui.backdrop_sampling_enabled") it.result = true
                }
            }
        }
        runCatching {
            "com.miui.home.launcher.common.Utilities".hookBeforeMethod(
                "canLockTaskView"
            ) { it.result = true }
        }
        runCatching {
            "com.miui.home.launcher.MIUIWidgetUtil".hookBeforeMethod(
                "isMIUIWidgetSupport"
            ) {
                it.result = true
            }
        }
        runCatching {
            "com.miui.home.launcher.MiuiHomeLog".findClass().replaceMethod(
                "log", String::class.java, String::class.java
            ) {
                return@replaceMethod null
            }
        }
        runCatching {
            "com.xiaomi.onetrack.OneTrack".hookBeforeMethod("isDisable") {
                it.result = true
            }
        }
    }
}
