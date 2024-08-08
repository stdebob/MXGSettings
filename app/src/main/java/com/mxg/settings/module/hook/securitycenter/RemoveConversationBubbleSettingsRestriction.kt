// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter

import android.annotation.SuppressLint
import android.content.Context
import android.util.ArrayMap
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.getObjectField
import org.lsposed.hiddenapibypass.HiddenApiBypass

class RemoveConversationBubbleSettingsRestriction : BaseHook() {
    @SuppressLint("PrivateApi")
    override fun init() {
        loadClass("com.miui.bubbles.settings.BubblesSettings").methodFinder()
            .filterByName("getDefaultBubbles")
            .single().createHook {
                before { param ->
                    val classBubbleApp = loadClass("com.miui.bubbles.settings.BubbleApp")
                    val arrayMap = ArrayMap<String, Any>()
                    val mContext =
                        param.thisObject.getObjectField("mContext") as Context
                    val mCurrentUserId =
                        param.thisObject.getObjectField("mCurrentUserId") as Int
                    val freeformSuggestionList = HiddenApiBypass.invoke(
                        Class.forName("android.util.MiuiMultiWindowUtils"),
                        null,
                        "getFreeformSuggestionList",
                        mContext
                    ) as List<*>
                    if (freeformSuggestionList.isNotEmpty()) {
                        for (str in freeformSuggestionList) {
                            val bubbleApp = classBubbleApp.getConstructor(
                                String::class.java, Int::class.java
                            ).newInstance(str, mCurrentUserId)
                            classBubbleApp.getMethod("setChecked", Boolean::class.java)
                                .invoke(bubbleApp, true)
                            arrayMap[str as String] = bubbleApp
                        }
                    }
                    param.result = arrayMap
                }
            }
    }
}
