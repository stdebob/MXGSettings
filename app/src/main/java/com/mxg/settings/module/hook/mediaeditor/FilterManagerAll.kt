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
package com.mxg.settings.module.hook.mediaeditor

import android.os.*
import com.github.kyuubiran.ezxhelper.ClassLoaderProvider.safeClassLoader
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.addUsingStringsEquals
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod
import com.mxg.settings.utils.api.LazyClass.AndroidBuildCls


object FilterManagerAll : BaseHook() {
    private lateinit var device: String
    private val methodResult by lazy {
        DexKit.getDexKitBridge("FilterManagerAll") { dexkit ->
            dexkit.findMethod {
                matcher {
                    addUsingStringsEquals("wayne")
                }
            }.filter { it.isMethod }.map { it.getMethodInstance(safeClassLoader) }.toTypedArray().firstOrNull()
        }.toMethod()
    }

    override fun init() {
        methodResult.createHook {
            before {
                if (!this@FilterManagerAll::device.isInitialized) {
                    device = Build.DEVICE
                }
                setStaticObject(AndroidBuildCls, "DEVICE", "wayne")
            }
            after {
                setStaticObject(AndroidBuildCls, "DEVICE", device)
            }
        }
    }
}
