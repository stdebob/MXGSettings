// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.framework;

import android.os.Bundle;
import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.prefs.RecommendPreference;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.PhoneFragment;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;

public class NetworkSettings extends SettingsPreferenceFragment {
    RecommendPreference mRecommend;

    @Override
    public int getContentResId() {
        return R.xml.framework_phone;
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity)getActivity()).showRestartSystemDialog();
    }

    @Override
    public void initPrefs() {

        Bundle args1 = new Bundle();
        mRecommend = new RecommendPreference(getContext());
        getPreferenceScreen().addPreference(mRecommend);

        args1.putString(":settings:fragment_args_key", "prefs_key_phone_additional_network_settings");
        mRecommend.addRecommendView(getString(R.string.phone_additional_network_settings),
                null,
                PhoneFragment.class,
                args1,
                R.string.phone
        );

    }
}
