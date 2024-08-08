// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.title

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.*
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.module.base.BaseXposedInit
import de.robv.android.xposed.callbacks.XC_InitPackageResources

object EnableIconMonetColor : BaseHook() {

    @SuppressLint("DiscouragedApi")
    override fun init() {
        val monet = "system_accent1_100"
        val monoColorId = Resources.getSystem().getIdentifier(monet, "color", "android")
        var monoColor = Resources.getSystem().getColor(monoColorId, null)
        if (BaseXposedInit.mPrefsMap.getBoolean("home_other_use_edit_color")) {
            monoColor = mPrefsMap.getInt("home_other_your_color_qwq", -1)
        }
        mResHook.setObjectReplacement(
            "com.miui.home",
            "color",
            "monochrome_default",
            monoColor
        )
    }
/*
    @SuppressLint("DiscouragedApi")
    fun initResource(resParam: XC_InitPackageResources.InitPackageResourcesParam) {
        val monet = "system_accent1_100"
        val monoColorId = Resources.getSystem().getIdentifier(monet, "color", "android")
        var monoColor = Resources.getSystem().getColor(monoColorId, null)
        if (BaseXposedInit.mPrefsMap.getBoolean("home_other_use_edit_color")) {
            monoColor = mPrefsMap.getInt("home_other_your_color_qwq", -1)
        }
        resParam.res.setReplacement(
            "com.miui.home",
            "color",
            "monochrome_default",
            monoColor
        )
//        val ColorEntriesId = Resources.getSystem().getStringArray()
//        val ColorEntries = Resources.getSystem().getStringArray(ColorEntriesId)
//        getInitPackageResourcesParam().res.setReplacement(
//            "com.miui.home",
//            "string",
//            ColorEntries.toString(),
//            "Monet"
//        )
    }*/
}
