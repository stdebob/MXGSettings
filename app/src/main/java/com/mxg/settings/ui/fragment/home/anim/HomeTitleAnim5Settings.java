// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.home.anim;

import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;

import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;

import moralnorm.preference.SeekBarPreferenceEx;

public class HomeTitleAnim5Settings extends SettingsPreferenceFragment {
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
        return R.xml.home_title_anim_5;
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
        mDRCX = findPreference("prefs_key_home_title_custom_anim_param_damping_RECT_CENTERX_5");
        mSRCX = findPreference("prefs_key_home_title_custom_anim_param_stiffness_RECT_CENTERX_5");
        mDRCY = findPreference("prefs_key_home_title_custom_anim_param_damping_RECT_CENTERY_5");
        mSRCY = findPreference("prefs_key_home_title_custom_anim_param_stiffness_RECT_CENTERY_5");
        mDRW = findPreference("prefs_key_home_title_custom_anim_param_damping_RECT_WIDTH_5");
        mSRW = findPreference("prefs_key_home_title_custom_anim_param_stiffness_RECT_WIDTH_5");
        mDRR = findPreference("prefs_key_home_title_custom_anim_param_damping_RECT_RATIO_5");
        mSRR = findPreference("prefs_key_home_title_custom_anim_param_stiffness_RECT_RATIO_5");
        mDR = findPreference("prefs_key_home_title_custom_anim_param_damping_RADIUS_5");
        mSR = findPreference("prefs_key_home_title_custom_anim_param_stiffness_RADIUS_5");
        mDA = findPreference("prefs_key_home_title_custom_anim_param_damping_ALPHA_5");
        mSA = findPreference("prefs_key_home_title_custom_anim_param_stiffness_ALPHA_5");

        if (isMoreHyperOSVersion(1f)) {
            mDRCX.setDefaultValue(880);
            mSRCX.setDefaultValue(460);
            mDRCY.setDefaultValue(880);
            mSRCY.setDefaultValue(460);
            mDRW.setDefaultValue(850);
            mSRW.setDefaultValue(460);
            mDRR.setDefaultValue(1000);
            mSRR.setDefaultValue(350);
            mDR.setDefaultValue(1000);
            mSR.setDefaultValue(350);
            mDA.setDefaultValue(1000);
            mSA.setDefaultValue(400);
        } else {
            mDRCX.setDefaultValue(990);
            mSRCX.setDefaultValue(450);
            mDRCY.setDefaultValue(990);
            mSRCY.setDefaultValue(450);
            mDRW.setDefaultValue(900);
            mSRW.setDefaultValue(450);
            mDRR.setDefaultValue(990);
            mSRR.setDefaultValue(370);
            mDR.setDefaultValue(990);
            mSR.setDefaultValue(150);
            mDA.setDefaultValue(990);
            mSA.setDefaultValue(420);
        }
    }
}
