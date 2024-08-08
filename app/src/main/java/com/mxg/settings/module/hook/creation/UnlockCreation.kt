// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.creation

import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.api.LazyClass.clazzMiuiBuild

object UnlockCreation : BaseHook() {
    override fun init() {
        setStaticObject(clazzMiuiBuild, "IS_TABLET", true)
    }
}
