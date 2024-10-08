// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter.battery

import android.os.*
import com.github.kyuubiran.ezxhelper.*
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createAfterHook
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.toElementList
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod
import com.mxg.settings.utils.*
import de.robv.android.xposed.*
import org.luckypray.dexkit.query.enums.*


object BatteryHealth : BaseHook() {
    private val getSecurityBatteryHealth by lazy {
        DexKit.getDexKitBridge("getSecurityBatteryHealth") {
            it.findMethod {
                matcher {
                    addUsingString("battery_health_soh", StringMatchType.Equals)
                }
            }.single().getMethodInstance(EzXHelper.classLoader)
        }.toMethod()
    }

    private val cc by lazy {
        DexKit.useDexkitIfNoCache(arrayOf("SecurityBatteryHealthClass")) {
            it.findClass {
                searchPackages("com.miui.powercenter.nightcharge")
                findFirst = true
                matcher {
                    methods {
                        add {
                            name = "handleMessage"
                        }
                    }
                }
            }
        }
    }

    private lateinit var gff: Any
    private var health: Int? = null


    override fun init() {
        getSecurityBatteryHealth.createAfterHook { param ->
            health = param.args[0] as Int // 获取手机管家内部的健康度
        }

        findAndHookMethod(
            "com.miui.powercenter.nightcharge.SmartChargeFragment",
            "onCreatePreferences",
            Bundle::class.java, String::class.java,
            object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    gff = param.thisObject
                        .callMethod("findPreference", "reference_battery_health")!!
                }
            }
        )

        val nameClass = DexKit.getDexKitBridgeList("SecurityBatteryHealthClass") { _ ->
            cc?.toElementList()
        }.toClassList().first().name
        findAndHookMethod(
            nameClass,
            "handleMessage",
            Message::class.java,
            object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    gff.callMethod("setText", "$health %")
                }
            })
    }
}
