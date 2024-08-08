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
import com.mxg.settings.module.hook.mms.DisableAd;
import com.mxg.settings.module.hook.mms.DisableRiskTip;
import com.mxg.settings.module.hook.various.UnlockSuperClipboard;

@HookExpand(pkg = "com.android.mms", isPad = false, tarAndroid = 33)
public class Mms extends BaseModule {
    @Override
    public void handleLoadPackage() {
        initHook(new DisableRiskTip(), mPrefsMap.getBoolean("mms_disable_fraud_risk_tip") || mPrefsMap.getBoolean("mms_disable_overseas_risk_tip"));
        initHook(new DisableAd(), mPrefsMap.getBoolean("mms_disable_ad"));
        initHook(UnlockSuperClipboard.INSTANCE, mPrefsMap.getStringAsInt("various_super_clipboard_e", 0) != 0);
    }
}
