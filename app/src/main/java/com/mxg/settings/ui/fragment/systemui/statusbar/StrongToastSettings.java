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
package com.mxg.settings.ui.fragment.systemui.statusbar;

import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;
import com.mxg.settings.utils.api.miuiStringToast.MiuiStringToast;

import moralnorm.preference.Preference;

public class StrongToastSettings extends SettingsPreferenceFragment {
    Preference mShortToast;
    Preference mLongToast;

    @Override
    public int getContentResId() { return R.xml.system_ui_status_bar_strong_toast; }

    @Override
    public void initPrefs() {
        mShortToast = findPreference("prefs_key_system_ui_status_bar_strong_toast_test_short_text");
        mLongToast = findPreference("prefs_key_system_ui_status_bar_strong_toast_test_long_text");

        mShortToast.setOnPreferenceClickListener(preference -> {
            MiuiStringToast.INSTANCE.showStringToast(requireActivity(), getResources().getString(R.string.system_ui_status_bar_strong_toast_test_short_text_0), 1);
            return true;
        });
        mLongToast.setOnPreferenceClickListener(preference -> {
            MiuiStringToast.INSTANCE.showStringToast(requireActivity(), getResources().getString(R.string.system_ui_status_bar_strong_toast_test_long_text_1), 1);
            return true;
        });
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity)getActivity()).showRestartDialog(
            getResources().getString(R.string.system_ui),
            "com.android.systemui"
        );
    }
}
