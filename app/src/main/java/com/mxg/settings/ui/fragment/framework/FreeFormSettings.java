// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
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
