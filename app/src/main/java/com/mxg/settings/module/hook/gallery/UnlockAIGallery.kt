// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.gallery

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*

object UnlockAIGallery : BaseHook() {
    override fun init() {
        // by 聖小熊
        loadClass("com.miui.gallery.ai.utils.AiGalleryUtil\$Companion").methodFinder()
            .filterByName("hasUseAccess")
            .first().createHook {
                returnConstant(true)
            }

        loadClass("com.miui.gallery.ai.widget.AiEntranceView").methodFinder()
            .filterByName("setVisibility")
            .first().createHook {
                returnConstant(null)
            }
    }
}
