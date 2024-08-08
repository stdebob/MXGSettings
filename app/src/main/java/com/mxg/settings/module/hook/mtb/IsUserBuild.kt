// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.mtb

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

object IsUserBuild : BaseHook() {
    override fun init() {
        loadClass("com.xiaomi.mtb.MtbUtils").methodFinder()
            .filterByName("IsUserBuild")
            .single().createHook {
                returnConstant(false)
        }
    }
}
