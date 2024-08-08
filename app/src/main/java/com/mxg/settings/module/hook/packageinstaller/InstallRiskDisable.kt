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
package com.mxg.settings.module.hook.packageinstaller

import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.toElementList
import org.luckypray.dexkit.query.enums.*

object InstallRiskDisable : BaseHook() {
    override fun init() {
        DexKit.getDexKitBridgeList("InstallRiskDisable") {
            it.findMethod {
                matcher {
                    addUsingString("secure_verify_enable", StringMatchType.Equals)
                    returnType = "boolean"
                }
                matcher {
                    addUsingString("installerOpenSafetyModel", StringMatchType.Equals)
                    returnType = "boolean"
                }
                matcher {
                    addUsingString("android.provider.MiuiSettings\$Ad", StringMatchType.Equals)
                    returnType = "boolean"
                }
            }.toElementList()
        }.toMethodList().createHooks {
            returnConstant(false)
        }
    }
}
