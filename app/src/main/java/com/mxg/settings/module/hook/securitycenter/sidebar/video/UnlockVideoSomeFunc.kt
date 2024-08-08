// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter.sidebar.video

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.toElementList
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethodDataList
import org.luckypray.dexkit.query.enums.*
import java.lang.reflect.*

object UnlockVideoSomeFunc : BaseHook() {
    private val findFrc by lazy {
        DexKit.useDexkitIfNoCache(arrayOf("findFrcA", "findFrcB")) {
            it.findMethod {
                matcher {
                    declaredClass {
                        addUsingString("ro.vendor.media.video.frc.support", StringMatchType.Equals)
                    }
                    returnType = "boolean"
                    paramTypes("java.lang.String")
                }
            }
        }
    }
    private val findTat by lazy {
        DexKit.getDexKitBridge("findTat") {
            it.findMethod {
                matcher {
                    declaredClass {
                        addUsingString("ro.vendor.media.video.frc.support", StringMatchType.Equals)
                    }
                    addUsingString("debug.config.media.video.ais.support", StringMatchType.Equals)
                }
            }.single().getMethodInstance(classLoader)
        }.toMethod()
    }

    private val memc by lazy {
        // 动态画面补偿
        mPrefsMap.getBoolean("security_center_unlock_memc")
    }
    private val enhance by lazy {
        // 影像轮廓增强
        mPrefsMap.getBoolean("security_center_unlock_enhance_contours")
    }
   private val resolution by lazy {
        // 极清播放
        mPrefsMap.getBoolean("security_center_unlock_s_resolution")
   }

    override fun init() {
        val orderedB = DexKit.getDexKitBridgeList("findFrcB") { _ ->
            findFrc?.toMethodDataList()?.filter { methodData ->
                methodData.usingFields.any {
                    it.field.typeName == "java.util.List"
                }
            }?.toElementList()
        }.toMethodList()
        val orderedA = DexKit.getDexKitBridgeList("findFrcA") { _ ->
            findFrc?.toElementList()
        }.toMethodList()
        val differentItems = orderedA.subtract(orderedB)

        if (memc) {
            differentItems.forEach { methods ->
                logD(TAG, lpparam.packageName, "find MeMc Method is $methods")
                hook(methods)
            }
        }

        var counter = 0
        orderedA.forEach { methods ->
            counter++
            if ((resolution || enhance) && counter == 1) {
                logD(TAG, lpparam.packageName, "find Tat Method is $findTat")
            }

            if (counter == 1 && resolution) {
                logD(TAG, lpparam.packageName, "find SuperResolution Method is $methods")

                hook(methods)
                hook(findTat)
            } else if (counter == 3 && enhance) {
                logD(TAG, lpparam.packageName, "find EnhanceContours Method is $methods")
                hook(methods)

                val newChar = findTat.name.toCharArray()
                for (i in newChar.indices) {
                    newChar[i]++
                }
                val newName = String(newChar)
                findTat.declaringClass.methodFinder()
                    .filterByName(newName)
                    .first().createHook {
                        returnConstant(true)
                    }
            }
        }
    }

    private fun hook(methods: Method) {
        methods.createHook {
            returnConstant(true)
        }
    }
}
