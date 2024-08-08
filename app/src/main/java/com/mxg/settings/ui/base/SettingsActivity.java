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
package com.mxg.settings.ui.base;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.mxg.settings.ui.SubSettings;
import com.mxg.settings.ui.fragment.framework.OtherSettings;
import com.mxg.settings.ui.fragment.home.HomeDockSettings;
import com.mxg.settings.ui.fragment.home.HomeFolderSettings;
import com.mxg.settings.ui.fragment.home.HomeGestureSettings;
import com.mxg.settings.ui.fragment.sub.MultiActionSettings;
import com.mxg.settings.ui.fragment.various.AlertDialogSettings;

import moralnorm.preference.Preference;
import moralnorm.preference.PreferenceFragmentCompat;

public abstract class SettingsActivity extends BaseSettingsActivity implements PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCreate();
    }

    public void initCreate() {}

    public void onStartSettingsForArguments(Preference preference, boolean isBundleEnable) {
        mProxy.onStartSettingsForArguments(SubSettings.class, preference, isBundleEnable);
    }

    @Override
    public boolean onPreferenceStartFragment(@NonNull PreferenceFragmentCompat preferenceFragmentCompat, @NonNull Preference preference) {
        boolean isBundleEnable = preferenceFragmentCompat instanceof OtherSettings ||
            preferenceFragmentCompat instanceof HomeDockSettings ||
            preferenceFragmentCompat instanceof HomeFolderSettings ||
            preferenceFragmentCompat instanceof AlertDialogSettings ||
            preferenceFragmentCompat instanceof HomeGestureSettings ||
            preferenceFragmentCompat instanceof MultiActionSettings;
        onStartSettingsForArguments(preference, isBundleEnable);
        return true;
    }
}
