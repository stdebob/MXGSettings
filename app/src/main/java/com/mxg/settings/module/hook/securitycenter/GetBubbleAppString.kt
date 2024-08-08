// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.getObjectField


object GetBubbleAppString : BaseHook() {
    private val classBubble by lazy {
        loadClass("com.miui.bubbles.Bubble")
    }

    override fun init() {
        loadClass("com.miui.bubbles.settings.BubblesSettings").methodFinder()
            .filterByName("getBubbleAppString")
            .first().createHook {
                before {
                    val stringBuilder = StringBuilder()
                    val mActiveBubbles =
                        it.thisObject.getObjectField("mActiveBubbles") as HashSet<*>
                    for (bubble in mActiveBubbles) {
                        stringBuilder.append(
                            classBubble.getMethod("getPackageName").invoke(bubble)
                        )
                        stringBuilder.append(":")
                        stringBuilder.append(bubble.getObjectField("userId"))
                        stringBuilder.append(",")
                    }
                    // XposedBridge.log("MaxFreeFormTest: getBubbleAppString called! Result:$stringBuilder")
                    it.result = stringBuilder.toString()
                }
            }
    }

}
