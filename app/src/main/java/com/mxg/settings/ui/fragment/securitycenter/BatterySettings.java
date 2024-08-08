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
package com.mxg.settings.ui.fragment.securitycenter;

import static com.hchen.hooktool.utils.SystemSDK.isPad;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;

import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;

public class BatterySettings extends SecurityCenterBaseSettings {
    @Override
    public int getContentResId() {
        return R.xml.security_center_battery;
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity) getActivity()).showRestartDialog(
                isPad() ? getResources().getString(R.string.security_center_pad) : isMoreHyperOSVersion(1f) ? getResources().getString(R.string.security_center_hyperos) : getResources().getString(R.string.security_center),
                "com.miui.securitycenter"
        );
    }
}
