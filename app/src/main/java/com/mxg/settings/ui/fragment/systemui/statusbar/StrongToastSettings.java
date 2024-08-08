// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.systemui.statusbar;

import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;
import com.mxg.settings.utils.api.miuiStringToast.MiuiStringToast;

import moralnorm.preference.Preference;

public class StrongToastSettings extends SettingsPreferenceFragment {
    Preference mShortToast;
    Preference mLongToast;

    @Override
    public int getContentResId() { return R.xml.system_ui_status_bar_strong_toast; }

    @Override
    public void initPrefs() {
        mShortToast = findPreference("prefs_key_system_ui_status_bar_strong_toast_test_short_text");
        mLongToast = findPreference("prefs_key_system_ui_status_bar_strong_toast_test_long_text");

        mShortToast.setOnPreferenceClickListener(preference -> {
            MiuiStringToast.INSTANCE.showStringToast(requireActivity(), getResources().getString(R.string.system_ui_status_bar_strong_toast_test_short_text_0), 1);
            return true;
        });
        mLongToast.setOnPreferenceClickListener(preference -> {
            MiuiStringToast.INSTANCE.showStringToast(requireActivity(), getResources().getString(R.string.system_ui_status_bar_strong_toast_test_long_text_1), 1);
            return true;
        });
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity)getActivity()).showRestartDialog(
            getResources().getString(R.string.system_ui),
            "com.android.systemui"
        );
    }
}
