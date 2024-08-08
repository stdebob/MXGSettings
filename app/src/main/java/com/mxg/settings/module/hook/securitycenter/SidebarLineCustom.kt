// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter

import com.mxg.settings.module.base.BaseHook

import de.robv.android.xposed.callbacks.XC_InitPackageResources

object SidebarLineCustom : BaseHook() {

    override fun init() {
        val mSidebarLineColorDefault =
            mPrefsMap.getInt("security_center_sidebar_line_color_default", -1294740525)
        val mSidebarLineColorDark =
            mPrefsMap.getInt("security_center_sidebar_line_color_dark", -6842473)
        val mSidebarLineColorLight =
            mPrefsMap.getInt("security_center_sidebar_line_color_light", -872415232)
        logI(
            TAG,
            "com.miui.securitycenter",
            "mSidebarLineColorDefault is $mSidebarLineColorDefault"
        )
        logI(TAG, "com.miui.securitycenter", "mSidebarLineColorDark is $mSidebarLineColorDark")
        logI(TAG, "com.miui.securitycenter", "mSidebarLineColorLight is $mSidebarLineColorLight")
        mResHook.setResReplacement(
            "com.miui.securitycenter",
            "color",
            "sidebar_line_color",
            mSidebarLineColorDefault
        )
        mResHook.setResReplacement(
            "com.miui.securitycenter",
            "color",
            "sidebar_line_color_dark",
            mSidebarLineColorLight
        )
        mResHook.setResReplacement(
            "com.miui.securitycenter",
            "color",
            "sidebar_line_color_light",
            mSidebarLineColorDark
        )
    }
/*
    fun initResource(resParam: XC_InitPackageResources.InitPackageResourcesParam) {
        val mSidebarLineColorDefault =
            mPrefsMap.getInt("security_center_sidebar_line_color_default", -1294740525)
        val mSidebarLineColorDark =
            mPrefsMap.getInt("security_center_sidebar_line_color_dark", -6842473)
        val mSidebarLineColorLight =
            mPrefsMap.getInt("security_center_sidebar_line_color_light", -872415232)
        logI(
            TAG,
            "com.miui.securitycenter",
            "mSidebarLineColorDefault is $mSidebarLineColorDefault"
        )
        logI(TAG, "com.miui.securitycenter", "mSidebarLineColorDark is $mSidebarLineColorDark")
        logI(TAG, "com.miui.securitycenter", "mSidebarLineColorLight is $mSidebarLineColorLight")
        resParam.res.setReplacement(
            "com.miui.securitycenter",
            "color",
            "sidebar_line_color",
            mSidebarLineColorDefault
        )
        resParam.res.setReplacement(
            "com.miui.securitycenter",
            "color",
            "sidebar_line_color_dark",
            mSidebarLineColorLight
        )
        resParam.res.setReplacement(
            "com.miui.securitycenter",
            "color",
            "sidebar_line_color_light",
            mSidebarLineColorDark
        )
    }*/


}
