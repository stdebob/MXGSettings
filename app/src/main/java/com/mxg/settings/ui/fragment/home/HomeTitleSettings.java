// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.home;

import static com.mxg.settings.utils.devicesdk.MiDeviceAppUtilsKt.isPad;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreAndroidVersion;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.prefs.RecommendPreference;
import com.mxg.settings.ui.SubPickerActivity;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;
import com.mxg.settings.ui.fragment.sub.AppPicker;

import moralnorm.preference.Preference;
import moralnorm.preference.PreferenceCategory;
import moralnorm.preference.SwitchPreference;

public class HomeTitleSettings extends SettingsPreferenceFragment {

    SwitchPreference mDisableMonoChrome;
    SwitchPreference mDisableMonetColor;
    SwitchPreference mDisableHideTheme;
    Preference mIconTitleCustomization;
    RecommendPreference mRecommend;
    PreferenceCategory mAppBlur;

    @Override
    public int getContentResId() {
        return R.xml.home_title;
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity) getActivity()).showRestartDialog(
            getResources().getString(R.string.mihome),
            "com.miui.home"
        );
    }

    @Override
    public void initPrefs() {
        mIconTitleCustomization = findPreference("prefs_key_home_title_title_icontitlecustomization");
        mDisableMonoChrome = findPreference("prefs_key_home_other_icon_mono_chrome");
        mAppBlur = findPreference("prefs_key_home_title_app_blur_hyper");

        mDisableMonoChrome.setVisible(isMoreAndroidVersion(33));
        mDisableMonoChrome.setOnPreferenceChangeListener((preference, o) -> true);
        mDisableMonetColor = findPreference("prefs_key_home_other_icon_monet_color");
        mDisableMonetColor.setVisible(isMoreAndroidVersion(33));
        mDisableMonetColor.setOnPreferenceChangeListener((preference, o) -> true);
        mDisableHideTheme = findPreference("prefs_key_home_title_disable_hide_theme");
        mDisableHideTheme.setVisible(isPad());
        mAppBlur.setVisible(isMoreHyperOSVersion(1f));

        mIconTitleCustomization.setOnPreferenceClickListener(preference -> {
            Intent intent = new Intent(getActivity(), SubPickerActivity.class);
            intent.putExtra("mode", AppPicker.INPUT_MODE);
            intent.putExtra("key", preference.getKey());
            startActivity(intent);
            return true;
        });


        Bundle args1 = new Bundle();
        Bundle args2 = new Bundle();
        mRecommend = new RecommendPreference(getContext());
        getPreferenceScreen().addPreference(mRecommend);

        args1.putString(":settings:fragment_args_key", "prefs_key_home_other_shortcut_background_blur");
        mRecommend.addRecommendView(getString(R.string.home_other_shortcut_background_blur),
                null,
                HomeOtherSettings.class,
                args1,
                R.string.home_other
        );

        args2.putString(":settings:fragment_args_key", "prefs_key_home_other_all_hide_app_activity");
        mRecommend.addRecommendView(getString(R.string.home_other_app_icon_hide),
                null,
                HomeOtherSettings.class,
                args2,
                R.string.home_other
        );
    }
}
