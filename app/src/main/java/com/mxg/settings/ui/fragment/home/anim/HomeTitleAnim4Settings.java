// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.home.anim;

import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;

import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;

import moralnorm.preference.SeekBarPreferenceEx;

public class HomeTitleAnim4Settings extends SettingsPreferenceFragment {
    SeekBarPreferenceEx mDRCX;
    SeekBarPreferenceEx mSRCX;
    SeekBarPreferenceEx mDRCY;
    SeekBarPreferenceEx mSRCY;
    SeekBarPreferenceEx mDRW;
    SeekBarPreferenceEx mSRW;
    SeekBarPreferenceEx mDRR;
    SeekBarPreferenceEx mSRR;
    SeekBarPreferenceEx mDR;
    SeekBarPreferenceEx mSR;
    SeekBarPreferenceEx mDA;
    SeekBarPreferenceEx mSA;
    
    @Override
    public int getContentResId() {
        return R.xml.home_title_anim_4;
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
        mDRCX = findPreference("prefs_key_home_title_custom_anim_param_damping_RECT_CENTERX_4");
        mSRCX = findPreference("prefs_key_home_title_custom_anim_param_stiffness_RECT_CENTERX_4");
        mDRCY = findPreference("prefs_key_home_title_custom_anim_param_damping_RECT_CENTERY_4");
        mSRCY = findPreference("prefs_key_home_title_custom_anim_param_stiffness_RECT_CENTERY_4");
        mDRW = findPreference("prefs_key_home_title_custom_anim_param_damping_RECT_WIDTH_4");
        mSRW = findPreference("prefs_key_home_title_custom_anim_param_stiffness_RECT_WIDTH_4");
        mDRR = findPreference("prefs_key_home_title_custom_anim_param_damping_RECT_RATIO_4");
        mSRR = findPreference("prefs_key_home_title_custom_anim_param_stiffness_RECT_RATIO_4");
        mDR = findPreference("prefs_key_home_title_custom_anim_param_damping_RADIUS_4");
        mSR = findPreference("prefs_key_home_title_custom_anim_param_stiffness_RADIUS_4");
        mDA = findPreference("prefs_key_home_title_custom_anim_param_damping_ALPHA_4");
        mSA = findPreference("prefs_key_home_title_custom_anim_param_stiffness_ALPHA_4");

        if (isMoreHyperOSVersion(1f)) {
            mDRCX.setDefaultValue(900);
            mSRCX.setDefaultValue(400);
            mDRCY.setDefaultValue(900);
            mSRCY.setDefaultValue(400);
            mDRW.setDefaultValue(900);
            mSRW.setDefaultValue(400);
            mDRR.setDefaultValue(970);
            mSRR.setDefaultValue(350);
            mDR.setDefaultValue(900);
            mSR.setDefaultValue(400);
            mDA.setDefaultValue(900);
            mSA.setDefaultValue(400);
        } else {
            mDRCX.setDefaultValue(950);
            mSRCX.setDefaultValue(315);
            mDRCY.setDefaultValue(950);
            mSRCY.setDefaultValue(315);
            mDRW.setDefaultValue(950);
            mSRW.setDefaultValue(315);
            mDRR.setDefaultValue(950);
            mSRR.setDefaultValue(270);
            mDR.setDefaultValue(990);
            mSR.setDefaultValue(270);
            mDA.setDefaultValue(990);
            mSA.setDefaultValue(270);
        }
    }
}
