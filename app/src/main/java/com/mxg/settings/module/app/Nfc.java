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
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.nfc.AllowInformationScreen;
import com.mxg.settings.module.hook.nfc.DisableSound;

@HookExpand(pkg = "com.android.nfc", isPad = false, tarAndroid = 33)
public class Nfc extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new DisableSound(), mPrefsMap.getBoolean("nfc_disable_sound"));
        initHook(new AllowInformationScreen(), mPrefsMap.getBoolean("nfc_allow_information_screen"));
    }
}
