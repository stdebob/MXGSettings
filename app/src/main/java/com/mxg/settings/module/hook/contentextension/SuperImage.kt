// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.contentextension

import android.graphics.Bitmap
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

class SuperImage : BaseHook() {
    private val superImageUtils by lazy {
        loadClassOrNull("com.miui.contentextension.utils.SuperImageUtils")
    }

    override fun init() {
        superImageUtils!!.methodFinder()
            .filterByName("isSupportSuperImage")
            .single().createHook {
            returnConstant(true)
        }

        superImageUtils!!.methodFinder()
            .filterByName("isBitmapSupportSuperImage")
            .filterByParamTypes {
                it[0] == Bitmap::class.java
            }.single().createHook {
            returnConstant(true)
        }
    }
}
