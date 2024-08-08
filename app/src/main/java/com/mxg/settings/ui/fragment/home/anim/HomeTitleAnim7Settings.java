// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.home.anim;

import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;

import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;

import moralnorm.preference.SeekBarPreferenceEx;

public class HomeTitleAnim7Settings extends SettingsPreferenceFragment {
    SeekBarPreferenceEx mDRCX;
    SeekBarPreferenceEx mSRCX;
    SeekBarPreferenceEx mDRCY;
    SeekBarPreferenceEx mSRCY;
    SeekBarPreferenceEx mDRW;
    SeekBarPreferenceEx mSRW;
    SeekBarPreferenceEx mDRR;
    SeekBarPreferenceEx mSRR;
    @Override
    public int getContentResId() {
        return R.xml.home_title_anim_7;
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
        mDRCX = findPreference("prefs_key_home_title_custom_anim_param_damping_RECT_CENTERX_7");
        mSRCX = findPreference("prefs_key_home_title_custom_anim_param_stiffness_RECT_CENTERX_7");
        mDRCY = findPreference("prefs_key_home_title_custom_anim_param_damping_RECT_CENTERY_7");
        mSRCY = findPreference("prefs_key_home_title_custom_anim_param_stiffness_RECT_CENTERY_7");
        mDRW = findPreference("prefs_key_home_title_custom_anim_param_damping_RECT_WIDTH_7");
        mSRW = findPreference("prefs_key_home_title_custom_anim_param_stiffness_RECT_WIDTH_7");
        mDRR = findPreference("prefs_key_home_title_custom_anim_param_damping_RECT_RATIO_7");
        mSRR = findPreference("prefs_key_home_title_custom_anim_param_stiffness_RECT_RATIO_7");

        if (isMoreHyperOSVersion(1f)) {
            mDRCX.setDefaultValue(990);
            mSRCX.setDefaultValue(315);
            mDRCY.setDefaultValue(990);
            mSRCY.setDefaultValue(315);
            mDRW.setDefaultValue(990);
            mSRW.setDefaultValue(315);
            mDRR.setDefaultValue(990);
            mSRR.setDefaultValue(315);
        } else {
            mDRCX.setDefaultValue(990);
            mSRCX.setDefaultValue(315);
            mDRCY.setDefaultValue(990);
            mSRCY.setDefaultValue(315);
            mDRW.setDefaultValue(990);
            mSRW.setDefaultValue(315);
            mDRR.setDefaultValue(990);
            mSRR.setDefaultValue(315);
        }
    }
}
