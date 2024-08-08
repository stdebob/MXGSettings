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
package com.mxg.settings.module.hook.home.recent

import android.view.*
import android.widget.*
import com.mxg.settings.module.base.*
import com.mxg.settings.utils.*

object RemoveIcon : BaseHook() {
    override fun init() {
        "com.miui.home.recents.views.TaskViewHeader".hookAfterMethod("onFinishInflate") {
            val mImage = it.thisObject.getObjectField("mIconView") as ImageView
            mImage.visibility = View.GONE
        }
    }
}
