// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.widget

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.callMethod
import com.mxg.settings.utils.getObjectField
import com.mxg.settings.utils.getObjectFieldOrNull

object AllowMoveAllWidgetToMinus : BaseHook() {
    override fun init() {
        try {
            loadClass("com.miui.home.launcher.widget.MIUIWidgetHelper").methodFinder()
                .filterByName("canDragToPa")
                .filterByParamCount(2)
                .single().createHook {
                    before {
                        val dragInfo = it.args[1].callMethod("getDragInfo")
                        val i = dragInfo?.getObjectField("spanX")
                        val launcherCallbacks = it.args[0].callMethod("getLauncherCallbacks")
                        val dragController = it.args[0].callMethod("getDragController")
                        val isDraggingFromAssistant =
                            dragController?.callMethod("isDraggingFromAssistant") as Boolean
                        val isDraggingToAssistant =
                            dragController.callMethod("isDraggingToAssistant") as Boolean
                        it.result =
                            launcherCallbacks != null && !isDraggingFromAssistant && !isDraggingToAssistant && i != 1
                    }
                }
        } catch (e: Exception) {
            loadClass("com.miui.home.launcher.Workspace").methodFinder()
                .filterByName("canDragToPa")
                .single().createHook {
                    before {
                        val currentDragObject =
                            it.thisObject.getObjectFieldOrNull("mDragController")
                                ?.callMethod("getCurrentDragObject")
                        val dragInfo = currentDragObject?.callMethod("getDragInfo")
                        val i = dragInfo?.getObjectField("spanX")
                        val launcherCallbacks = it.thisObject.getObjectFieldOrNull("mLauncher")
                            ?.callMethod("getLauncherCallbacks")
                        val isDraggingFromAssistant =
                            it.thisObject.getObjectFieldOrNull("mDragController")
                                ?.callMethod("isDraggingFromAssistant") as Boolean
                        val isDraggingToAssistant =
                            it.thisObject.getObjectFieldOrNull("mDragController")
                                ?.callMethod("isDraggingToAssistant") as Boolean

                        it.result =
                            launcherCallbacks != null && !isDraggingFromAssistant && !isDraggingToAssistant && i != 1
                    }
                }
        }

    }
}
