// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.home.anim;

import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;

import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;

import moralnorm.preference.SeekBarPreferenceEx;

public class HomeTitleAnim2Settings extends SettingsPreferenceFragment {
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
        return R.xml.home_title_anim_2;
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
        mDRCX = findPreference("prefs_key_home_title_custom_anim_param_damping_RECT_CENTERX_2");
        mSRCX = findPreference("prefs_key_home_title_custom_anim_param_stiffness_RECT_CENTERX_2");
        mDRCY = findPreference("prefs_key_home_title_custom_anim_param_damping_RECT_CENTERY_2");
        mSRCY = findPreference("prefs_key_home_title_custom_anim_param_stiffness_RECT_CENTERY_2");
        mDRW = findPreference("prefs_key_home_title_custom_anim_param_damping_RECT_WIDTH_2");
        mSRW = findPreference("prefs_key_home_title_custom_anim_param_stiffness_RECT_WIDTH_2");
        mDRR = findPreference("prefs_key_home_title_custom_anim_param_damping_RECT_RATIO_2");
        mSRR = findPreference("prefs_key_home_title_custom_anim_param_stiffness_RECT_RATIO_2");
        mDR = findPreference("prefs_key_home_title_custom_anim_param_damping_RADIUS_2");
        mSR = findPreference("prefs_key_home_title_custom_anim_param_stiffness_RADIUS_2");
        mDA = findPreference("prefs_key_home_title_custom_anim_param_damping_ALPHA_2");
        mSA = findPreference("prefs_key_home_title_custom_anim_param_stiffness_ALPHA_2");

        if (isMoreHyperOSVersion(1f)) {
            mDRCX.setDefaultValue(1000);
            mSRCX.setDefaultValue(330);
            mDRCY.setDefaultValue(1000);
            mSRCY.setDefaultValue(330);
            mDRW.setDefaultValue(1000);
            mSRW.setDefaultValue(330);
            mDRR.setDefaultValue(1000);
            mSRR.setDefaultValue(330);
            mDR.setDefaultValue(1000);
            mSR.setDefaultValue(330);
            mDA.setDefaultValue(1000);
            mSA.setDefaultValue(200);
        } else {
            mDRCX.setDefaultValue(960);
            mSRCX.setDefaultValue(300);
            mDRCY.setDefaultValue(990);
            mSRCY.setDefaultValue(300);
            mDRW.setDefaultValue(960);
            mSRW.setDefaultValue(410);
            mDRR.setDefaultValue(960);
            mSRR.setDefaultValue(340);
            mDR.setDefaultValue(990);
            mSR.setDefaultValue(135);
            mDA.setDefaultValue(990);
            mSA.setDefaultValue(135);
        }
    }
}
