// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.personalassistant

import android.graphics.drawable.*
import android.view.*
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createAfterHook
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.addUsingStringsEquals
import com.mxg.settings.module.base.dexkit.DexKitTool.toElementList
import com.mxg.settings.utils.api.*
import kotlin.math.*

object BlurPersonalAssistant : BaseHook() {
    private val blurRadius by lazy {
        mPrefsMap.getInt("personal_assistant_blurradius", 80)
    }
    private val backgroundColor by lazy {
        mPrefsMap.getInt("personal_assistant_color", -1)
    }

    override fun init() {
        var lastBlurRadius = -1

        DexKit.getDexKitBridgeList("BlurPersonalAssistant") {
            it.findMethod {
                matcher {
                    addUsingStringsEquals("ScrollStateManager")
                }
            }.toElementList()
        }.toMethodList().forEach { methodData ->
            methodData.createAfterHook {
                val scrollX = it.args[0] as Float
                val fieldNames = ('a'..'z').map { name -> name.toString() }
                val window = getValueByFields(it.thisObject, fieldNames) ?: return@createAfterHook

                if (window.javaClass.name.contains("Window")) {
                    runCatching {
                        window as Window
                        val blurRadius = (scrollX * blurRadius).toInt()
                        if (abs(blurRadius - lastBlurRadius) > 2) {
                            window.setBackgroundBlurRadius(blurRadius)
                            lastBlurRadius = blurRadius
                        }
                        val backgroundColorDrawable = ColorDrawable(backgroundColor)
                        backgroundColorDrawable.alpha = (scrollX * 255).toInt()
                        window.setBackgroundDrawable(backgroundColorDrawable)
                    }
                }
            }
        }
    }
}
