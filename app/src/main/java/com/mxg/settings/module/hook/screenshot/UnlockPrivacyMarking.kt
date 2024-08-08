// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.screenshot

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook


object UnlockPrivacyMarking : BaseHook() {
    private val isClass by lazy {
        loadClass("com.miui.gallery.editor.photo.screen.mosaic.ScreenMosaicView")
    }

    override fun init() {
        isClass.methodFinder()
            .filterByName("isSupportPrivacyMarking")
            .single().createHook {
                returnConstant(true)
            }
    }
}
