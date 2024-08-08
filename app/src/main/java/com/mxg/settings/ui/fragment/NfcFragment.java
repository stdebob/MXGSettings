// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.prefs.RecommendPreference;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;

public class NfcFragment extends SettingsPreferenceFragment {
    RecommendPreference mRecommend;
    @Override
    public int getContentResId() {
        return R.xml.nfc;
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity)getActivity()).showRestartDialog(
            getResources().getString(R.string.nfc),
            "com.android.nfc"
        );
    }

    @Override
    public void initPrefs() {

        Bundle args1 = new Bundle();
        mRecommend = new RecommendPreference(getContext());
        getPreferenceScreen().addPreference(mRecommend);

        args1.putString(":settings:fragment_args_key", "prefs_key_tsmclient_auto_nfc");
        mRecommend.addRecommendView(getString(R.string.tsmclient_auto_nfc),
                null,
                TsmClientFragment.class,
                args1,
                R.string.tsmclient
        );

    }
}
