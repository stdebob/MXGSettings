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

import android.graphics.Typeface
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.getObjectFieldAs


object ForceClockUseSystemFontsHook : BaseHook() {
    override fun init() {
        loadClass("com.miui.clock.MiuiBaseClock").methodFinder().filter {
            name == "updateViewsTextSize"
        }.toList().createHooks {
            after { param ->
                val mTimeText =
                    param.thisObject.getObjectFieldAs<TextView>("mTimeText")
                mTimeText.typeface = Typeface.DEFAULT
            }
        }

        loadClass("com.miui.clock.MiuiLeftTopLargeClock").methodFinder().filter {
            name == "onLanguageChanged" && parameterTypes == String::class.java
        }.toList().createHooks {
            after { param ->
                val mTimeText =
                    param.thisObject.getObjectFieldAs<TextView>("mCurrentDateLarge")
                mTimeText.typeface = Typeface.DEFAULT
            }
        }
    }
}
