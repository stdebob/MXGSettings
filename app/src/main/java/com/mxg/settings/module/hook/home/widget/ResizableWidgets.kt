// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.widget

import android.appwidget.AppWidgetProviderInfo
import com.mxg.settings.module.base.BaseHook


object ResizableWidgets : BaseHook() {
    override fun init() {
        hookAllMethods(
            "android.appwidget.AppWidgetHostView",
            null,
            "getAppWidgetInfo",
            object : MethodHook() {
                @Throws(Throwable::class)
                override fun after(param: MethodHookParam) {
                    val widgetInfo = param.result as AppWidgetProviderInfo
                    widgetInfo.resizeMode =
                        AppWidgetProviderInfo.RESIZE_VERTICAL or AppWidgetProviderInfo.RESIZE_HORIZONTAL
                    widgetInfo.minHeight = 0
                    widgetInfo.minWidth = 0
                    widgetInfo.minResizeHeight = 0
                    widgetInfo.minResizeWidth = 0
                    param.result = widgetInfo
                }
            })
    }

}
