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
package com.mxg.settings.ui.fragment.framework;

import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreMiuiVersion;

import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;

import moralnorm.preference.SwitchPreference;

public class FreeFormSettings extends SettingsPreferenceFragment {

    SwitchPreference mMoreFreeForm; // 多小窗

    @Override
    public int getContentResId() {
        return R.xml.framework_freeform;
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity)getActivity()).showRestartSystemDialog();
    }

    @Override
    public void initPrefs() {
        mMoreFreeForm = findPreference("prefs_key_system_framework_freeform_count");
        mMoreFreeForm.setVisible(isMoreMiuiVersion(13f) || isMoreHyperOSVersion(1f));
    }
}
