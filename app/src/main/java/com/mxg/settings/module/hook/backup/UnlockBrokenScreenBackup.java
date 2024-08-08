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
package com.mxg.settings.module.hook.backup;

import android.os.Bundle;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class UnlockBrokenScreenBackup extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.miui.backup.settings.MoreSettingsFragment", "onCreatePreferences", Bundle.class, String.class, new MethodHook(){
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                XposedHelpers.setStaticBooleanField(findClassIfExists("com.miui.backup.settings.MoreSettingsFragment"), "IS_INTERNATIONAL", false);
            }
        });
    }
}
