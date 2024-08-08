/*
  * This file is part of HyperCeiler.

  * HyperCeiler is free software: you can redistribute it and/or modify
  * it under the terms of the GNU Affero General Public License as
  * published by the Free Software Foundation, either version 3 of the
  * License.

  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU Affero General Public License for more details.

  * You should have received a copy of the GNU Affero General Public License
  * along with this program.  If not, see <https://www.gnu.org/licenses/>.

  * Copyright (C) 2023-2024 HyperCeiler Contributions
*/
package com.mxg.settings.module.hook.systemframework

import android.content.Context
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.api.field


object RemoveSmallWindowRestrictions : BaseHook() {
    private val mSettingsClass =
        loadClass("com.android.server.wm.WindowManagerService\$SettingsObserver")
    private val mWindowsUtilsClass = loadClass("android.util.MiuiMultiWindowUtils")
    private val mWindowsClass = loadClass("android.util.MiuiMultiWindowAdapter")

    override fun init() {
        try {
            loadClass("com.android.server.wm.ActivityTaskManagerService").methodFinder()
                .filterByName("retrieveSettings")
                .single().createHook {
                    after { param ->
                        param.thisObject.javaClass.field("mDevEnableNonResizableMultiWindow")
                            .setBoolean(param.thisObject, true)
                    }
                }
        } catch (e: Throwable) {
            logE(TAG, this.lpparam.packageName, "Hook retrieveSettings failed by: $e")
        }

        try {
            mSettingsClass.methodFinder().filter {
                name == "updateDevEnableNonResizableMultiWindow"
            }.toList().createHooks {
                after { param ->
                    val this0 = param.thisObject.javaClass.field("this\$0").get(param.thisObject)
                    val mAtmService = this0.javaClass.field("mAtmService").get(this0)
                    mAtmService.javaClass.field("mDevEnableNonResizableMultiWindow")
                        .setBoolean(mAtmService, true)
                }
            }
        } catch (e: Throwable) {
            logE(
                TAG,
                this.lpparam.packageName,
                "Hook updateDevEnableNonResizableMultiWindow failed by: $e"
            )
        }

        try {
            mSettingsClass.methodFinder().filter {
                name == "onChange"
            }.toList().createHooks {
                after { param ->
                    val this0 = param.thisObject.javaClass.field("this\$0").get(param.thisObject)
                    val mAtmService = this0.javaClass.field("mAtmService").get(this0)
                    mAtmService.javaClass.field("mDevEnableNonResizableMultiWindow")
                        .setBoolean(mAtmService, true)
                }
            }
        } catch (e: Throwable) {
            logE(TAG, this.lpparam.packageName, "Hook onChange failed by: $e")
        }

        try {
            mWindowsUtilsClass.methodFinder()
                .filterByName("isForceResizeable")
                .single().createHook {
                    returnConstant(true)
                }
        } catch (e: Throwable) {
            logE(TAG, this.lpparam.packageName, "Hook isForceResizeable failed by: $e")
        }

        // Author: LittleTurtle2333
        try {
            loadClass("com.android.server.wm.Task").methodFinder()
                .filterByName("isResizeable")
                .single().createHook {
                    returnConstant(true)
                }
        } catch (e: Throwable) {
            logE(TAG, this.lpparam.packageName, "Hook isResizeable failed by: $e")
        }

        try {
            mWindowsClass.methodFinder()
                .filterByName("getFreeformBlackList")
                .single().createHook {
                    returnConstant(mutableListOf<String>())
                }
        } catch (e: Throwable) {
            logE(TAG, this.lpparam.packageName, "Hook getFreeformBlackList failed by: $e")
        }

        try {
            mWindowsClass.methodFinder()
                .filterByName("getFreeformBlackListFromCloud")
                .filterByParamTypes {
                    it[0] == Context::class.java
                }
                .single().createHook {
                    returnConstant(mutableListOf<String>())
                }
        } catch (e: Throwable) {
            logE(TAG, this.lpparam.packageName, "Hook getFreeformBlackListFromCloud failed by: $e")
        }

        try {
            mWindowsClass.methodFinder()
                .filterByName("getStartFromFreeformBlackListFromCloud")
                .single().createHook {
                    returnConstant(mutableListOf<String>())
                }
        } catch (e: Throwable) {
            logE(TAG, this.lpparam.packageName, "Hook getStartFromFreeformBlackListFromCloud failed by: $e")
        }

        try {
            mWindowsUtilsClass.methodFinder()
                .filterByName("supportFreeform")
                .single().createHook {
                    returnConstant(true)
                }
        } catch (e: Throwable) {
            logE(TAG, this.lpparam.packageName, "Hook supportFreeform failed by: $e")
        }

    }

}
