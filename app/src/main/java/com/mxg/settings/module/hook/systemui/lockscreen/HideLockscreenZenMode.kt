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
package com.mxg.settings.module.hook.systemui.lockscreen

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.devicesdk.isMoreAndroidVersion
import com.mxg.settings.utils.devicesdk.isMoreHyperOSVersion
import com.mxg.settings.utils.setObjectField

object HideLockscreenZenMode : BaseHook() {
    private val zenModeClass by lazy {
        loadClass("com.android.systemui.statusbar.notification.zen.ZenModeViewController")
    }

    override fun init() {
        // hyperOS fix by hyper helper
        if (isMoreHyperOSVersion(1f) && isMoreAndroidVersion(34)) {
            zenModeClass.methodFinder()
                .filterByName("updateVisibility")
                .single().createHook {
                    before {
                        it.thisObject.setObjectField("manuallyDismissed", true)
                    }
                }
        } else {
            zenModeClass.methodFinder()
                .filterByName("shouldBeVisible")
                .single().createHook {
                    returnConstant(false)
                }
        }
    }
}
