// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.statusbar.clock

import android.annotation.*
import android.graphics.*
import android.view.*
import android.widget.*
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.ConstructorFinder.`-Static`.constructorFinder
import com.mxg.settings.module.base.*
import com.mxg.settings.utils.devicesdk.DisplayUtils.*

object TimeStyle : BaseHook() {
    private val clockBold by lazy {
        mPrefsMap.getBoolean("system_ui_statusbar_clock_bold")
    }
    private val getMode by lazy {
        mPrefsMap.getStringAsInt("system_ui_statusbar_clock_mode", 0)
    }
    private val isAlign by lazy {
        mPrefsMap.getStringAsInt("system_ui_statusbar_clock_double_mode", 0)
    }
    private val isGeekAlign by lazy {
        mPrefsMap.getStringAsInt("system_ui_statusbar_clock_double_mode_geek", 0)
    }
    private val verticalOffset by lazy {
        mPrefsMap.getInt("system_ui_statusbar_clock_vertical_offset", 12)
    }
    private val isClockDouble by lazy {
        mPrefsMap.getBoolean("system_ui_statusbar_clock_double")
    }
    private val lineSpacing by lazy {
        mPrefsMap.getInt("system_ui_statusbar_clock_double_spacing_margin", 16)
    }
    private val lineSpacingGeek by lazy {
        mPrefsMap.getInt("system_ui_statusbar_clock_size_geek_spacing_margin", 16)
    }
    private val fixedWidth by lazy {
        mPrefsMap.getInt("system_ui_statusbar_clock_fixedcontent_width", 30)
    }

    private var leftMargin =
        mPrefsMap.getInt("system_ui_statusbar_clock_left_margin", 0)
    private var rightMargin =
        mPrefsMap.getInt("system_ui_statusbar_clock_right_margin", 0)

    private val mClockClass by lazy {
        loadClass("com.android.systemui.statusbar.views.MiuiClock")
    }

    @SuppressLint("RtlHardcoded", "DiscouragedApi")
    override fun init() {
        mClockClass.constructorFinder()
            .filterByParamCount(3)
            .first().createHook {
                after {
                    try {
                        val textV = it.thisObject as TextView

                        if (textV.resources.getResourceEntryName(textV.id) == "clock") {
                            // 时钟加粗
                            if (clockBold) {
                                textV.typeface = Typeface.DEFAULT_BOLD
                            }

                            // 时钟边距调整
                            margin(textV)

                            // 以下 Hook 需要启用自定义时钟指示器才能生效
                            if (getMode != 0) {
                                val alignment = when (getMode) {
                                    1 -> isAlign
                                    2 -> isGeekAlign
                                    else -> 0
                                }

                                textV.textAlignment = when (alignment) {
                                    1 -> View.TEXT_ALIGNMENT_CENTER
                                    2 -> View.TEXT_ALIGNMENT_TEXT_END
                                    else -> View.TEXT_ALIGNMENT_TEXT_START
                                }

                                // 双排时钟行间距调整
                                if ((getMode == 1 && isClockDouble) || getMode == 2) {
                                    textLineSpacing(textV)
                                }

                                // 固定宽度
                                if (fixedWidth > 30) {
                                    textV.width =
                                        (textV.resources.displayMetrics.density * fixedWidth).toInt()
                                }
                            }

                        }
                    } catch (_: Exception) {
                    }
                }
            }
    }

    private fun margin(id: TextView) {
        val left = dp2px(leftMargin.toFloat())
        val right = dp2px(rightMargin.toFloat())
        var topMargin = 0
        if (verticalOffset != 12) {
            topMargin = dp2px((verticalOffset - 12) * 0.5f)
        }
        id.setPaddingRelative(left, topMargin, right, 0)
    }

    private fun textLineSpacing(id: TextView) {
        when (getMode) {
            1 -> id.setLineSpacing(0f, lineSpacing * 0.05f)
            2 -> id.setLineSpacing(0f, lineSpacingGeek * 0.05f)
        }
    }
}
