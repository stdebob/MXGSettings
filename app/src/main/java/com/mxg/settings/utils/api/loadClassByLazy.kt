// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.utils.api

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull

// by StarVoyager
object LazyClass {
    val clazzMiuiBuild by lazy {
        loadClass("miui.os.Build")
    }

    val AndroidBuildCls by lazy {
        loadClass("android.os.Build")
    }

    val SettingsFeaturesClass by lazy {
        loadClass("com.android.settings.utils.SettingsFeatures")
    }

    val FeatureParserCls by lazy {
        loadClass("miui.util.FeatureParser")
    }

    val SystemProperties by lazy {
        loadClass("android.os.SystemProperties")
    }

    val MiuiBuildCls by lazy {
        loadClass("miui.os.Build")
    }

    val SettingsFeaturesCls by lazy {
        loadClass("com.android.settings.utils.SettingsFeatures")
    }

    val AiasstVisionSystemUtilsCls by lazy {
        loadClass("com.xiaomi.aiasst.vision.utils.SystemUtils")
    }

    val SupportAiSubtitlesUtils by lazy {
        loadClass("com.xiaomi.aiasst.vision.utils.SupportAiSubtitlesUtils")
    }

    val MiuiSettingsCls by lazy {
        loadClass("com.android.settings.MiuiSettings")
    }

    val mNewClockClass by lazy {
        loadClass("com.android.systemui.statusbar.views.MiuiStatusBarClock")
    }

    val StrongToast by lazy {
        loadClassOrNull("com.android.systemui.toast.MIUIStrongToast")
    }
}
