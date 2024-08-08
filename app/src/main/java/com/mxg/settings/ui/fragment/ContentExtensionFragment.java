// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment;

import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isAndroidVersion;

import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;

import moralnorm.preference.SwitchPreference;

public class ContentExtensionFragment extends SettingsPreferenceFragment {
    SwitchPreference mUnlockTaplus;

    @Override
    public int getContentResId() {
        return R.xml.content_extension;
    }

    @Override
    public void initPrefs() {
        mUnlockTaplus= findPreference("prefs_key_content_extension_unlock_taplus");

        mUnlockTaplus.setVisible(!isAndroidVersion(30));
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity)getActivity()).showRestartDialog(
            getResources().getString(R.string.content_extension),
            "com.miui.contentextension"
        );
    }
}
