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
package com.mxg.settings.module.hook.systemui.statusbar.strongtoast

import android.widget.FrameLayout
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.api.LazyClass.StrongToast


object HideStrongToast : BaseHook() {
    override fun init() {
        StrongToast!!.methodFinder()
            .filterByName("onAttachedToWindow")
            .single().createHook {
            after {
                val strongToastLayout = it.thisObject as FrameLayout
                strongToastLayout.viewTreeObserver.addOnPreDrawListener {
                    return@addOnPreDrawListener false
                }
            }
        }
    }
}
