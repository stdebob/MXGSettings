// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
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
