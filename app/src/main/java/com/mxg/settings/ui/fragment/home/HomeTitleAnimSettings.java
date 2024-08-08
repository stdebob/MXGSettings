// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.home;

import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;

import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;

import moralnorm.preference.Preference;

public class HomeTitleAnimSettings extends SettingsPreferenceFragment {
    Preference mPage1;
    Preference mPage9;
    @Override
    public int getContentResId() {
        return R.xml.home_title_anim;
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity)getActivity()).showRestartDialog(
            getResources().getString(R.string.mihome),
            "com.miui.home"
        );
    }

    @Override
    public void initPrefs() {
        mPage1 = findPreference("prefs_key_home_title_custom_anim_param_1_title");
        mPage9 = findPreference("prefs_key_home_title_custom_anim_param_9_title");

        mPage9.setVisible(isMoreHyperOSVersion(1f));
        mPage1.setVisible(!isMoreHyperOSVersion(1f));
    }
}
