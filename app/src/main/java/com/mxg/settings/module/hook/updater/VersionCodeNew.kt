// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.updater

import android.os.*
import android.text.*
import com.github.kyuubiran.ezxhelper.*
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.addUsingStringsEquals
import com.mxg.settings.module.base.dexkit.DexKitTool.toElementList
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod
import de.robv.android.xposed.*

object VersionCodeNew : BaseHook() {
    private val mBigMethod by lazy {
        DexKit.getDexKitBridge("VersionCodeNew1") {
            it.findMethod {
                matcher {
                    addUsingStringsEquals("ro.miui.ui.version.name")
                }
            }.single().getMethodInstance(EzXHelper.safeClassLoader)
        }.toMethod()
    }
    private val mOSMethod by lazy {
        DexKit.getDexKitBridgeList("VersionCodeNew2") {
            it.findMethod {
                matcher {
                    addUsingStringsEquals("ro.mi.os.version.incremental")
                }
            }.toElementList()
        }.toMethodList()
    }
    private val mOSCode by lazy {
        DexKit.getDexKitBridge("VersionCodeNew3") {
            it.findMethod {
                matcher {
                    addUsingStringsEquals("ro.mi.os.version.name", "OS")
                }
            }.single().getMethodInstance(EzXHelper.safeClassLoader)
        }.toMethod()
    }

    private val mOldVersionCode =
        mPrefsMap.getString("various_updater_big_version", "V816")

    private val mVersionCode =
        mPrefsMap.getString("various_updater_miui_version", "V14.0.22.11.26.DEV")


    override fun init() {
        // 原始修改版本名
        val mApplication = findClassIfExists("com.android.updater.Application")

        findAndHookMethod(mApplication, "onCreate", object : MethodHook() {
            override fun before(param: MethodHookParam) {
                if (!TextUtils.isEmpty(mVersionCode)) {
                    XposedHelpers.setStaticObjectField(
                        Build.VERSION::class.java,
                        "INCREMENTAL",
                        "$mOldVersionCode.${mVersionCode.substringAfter(".")}"
                    )
                }
            }
        })

        // 大版本名字修改
        mBigMethod.createHook {
            before {
                if (!TextUtils.isEmpty(mOldVersionCode)) {
                    it.result = mOldVersionCode.substringAfter("V")
                }
            }
        }

        // OS 版本名修改
        mOSMethod.createHooks {
            before {
                if (!TextUtils.isEmpty(mVersionCode)) {
                    it.result = mVersionCode
                }
            }
        }

        // OS 版本修改
        mOSCode.createHook {
            before {
                if (!TextUtils.isEmpty(mVersionCode)) {
                    it.result =
                        "${mVersionCode.split(".")[0]}.${mVersionCode.split(".")[1]}.${mVersionCode.split(".")[2]}"
                }
            }
        }
    }
}
