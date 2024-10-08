// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.home.anim;

import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;

import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;

import moralnorm.preference.SeekBarPreferenceEx;

public class HomeTitleAnim3Settings extends SettingsPreferenceFragment {
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
        return R.xml.home_title_anim_3;
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
        mDRCX = findPreference("prefs_key_home_title_custom_anim_param_damping_RECT_CENTERX_3");
        mSRCX = findPreference("prefs_key_home_title_custom_anim_param_stiffness_RECT_CENTERX_3");
        mDRCY = findPreference("prefs_key_home_title_custom_anim_param_damping_RECT_CENTERY_3");
        mSRCY = findPreference("prefs_key_home_title_custom_anim_param_stiffness_RECT_CENTERY_3");
        mDRW = findPreference("prefs_key_home_title_custom_anim_param_damping_RECT_WIDTH_3");
        mSRW = findPreference("prefs_key_home_title_custom_anim_param_stiffness_RECT_WIDTH_3");
        mDRR = findPreference("prefs_key_home_title_custom_anim_param_damping_RECT_RATIO_3");
        mSRR = findPreference("prefs_key_home_title_custom_anim_param_stiffness_RECT_RATIO_3");

        if (isMoreHyperOSVersion(1f)) {
            mDRCX.setDefaultValue(900);
            mSRCX.setDefaultValue(270);
            mDRCY.setDefaultValue(900);
            mSRCY.setDefaultValue(270);
            mDRW.setDefaultValue(990);
            mSRW.setDefaultValue(360);
            mDRR.setDefaultValue(990);
            mSRR.setDefaultValue(360);
        } else {
            mDRCX.setDefaultValue(900);
            mSRCX.setDefaultValue(270);
            mDRCY.setDefaultValue(900);
            mSRCY.setDefaultValue(270);
            mDRW.setDefaultValue(990);
            mSRW.setDefaultValue(360);
            mDRR.setDefaultValue(990);
            mSRR.setDefaultValue(360);
        }
    }
}
