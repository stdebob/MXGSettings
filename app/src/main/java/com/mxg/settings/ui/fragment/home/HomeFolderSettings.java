// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.home;

import static com.mxg.settings.utils.devicesdk.MiDeviceAppUtilsKt.isPad;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;

import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;
import com.mxg.settings.utils.prefs.PrefsUtils;

import moralnorm.preference.DropDownPreference;
import moralnorm.preference.SeekBarPreferenceEx;
import moralnorm.preference.SwitchPreference;

public class HomeFolderSettings extends SettingsPreferenceFragment {

    DropDownPreference mFolderShade;
    SeekBarPreferenceEx mFolderShadeLevel;

    SeekBarPreferenceEx mFolderColumns;
    SwitchPreference mFolderWidth;
    SwitchPreference mFolderSpace;
    SwitchPreference mUnlockFolderBlurSupport;
    SwitchPreference mSmallFolderIconBackgroundCustom1;
    SwitchPreference mSmallFolderIconBackgroundCustom2;
    SwitchPreference mSmallFolderIconBackgroundCustom3;

    @Override
    public int getContentResId() {
        return R.xml.home_folder;
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
        mFolderShade = findPreference("prefs_key_home_folder_shade");
        mFolderShadeLevel = findPreference("prefs_key_home_folder_shade_level");
        mUnlockFolderBlurSupport = findPreference("prefs_key_home_folder_unlock_blur_supported");

        mFolderColumns = findPreference("prefs_key_home_folder_columns");
        mFolderWidth = findPreference("prefs_key_home_folder_width");
        mFolderSpace = findPreference("prefs_key_home_folder_space");

        if (isPad()) {
            mSmallFolderIconBackgroundCustom1 = findPreference("prefs_key_home_big_folder_icon_bg_2x1");
            mSmallFolderIconBackgroundCustom2 = findPreference("prefs_key_home_big_folder_icon_bg_1x2");
            mSmallFolderIconBackgroundCustom3 = findPreference("prefs_key_home_big_folder_icon_bg");

            mSmallFolderIconBackgroundCustom1.setTitle(R.string.home_big_folder_icon_bg_2x1_n);
            mSmallFolderIconBackgroundCustom2.setTitle(R.string.home_big_folder_icon_bg_1x2_n);
            mSmallFolderIconBackgroundCustom3.setTitle(R.string.home_big_folder_icon_bg_n);
        }
        mUnlockFolderBlurSupport.setVisible(isMoreHyperOSVersion(1f));
        setFolderShadeLevelEnable(Integer.parseInt(PrefsUtils.mSharedPreferences.getString("prefs_key_home_folder_shade", "0")));
        setFolderWidthEnable(PrefsUtils.mSharedPreferences.getInt(mFolderColumns.getKey(), 3));
        setFolderSpaceEnable(PrefsUtils.mSharedPreferences.getInt(mFolderColumns.getKey(), 3));

        mFolderShade.setOnPreferenceChangeListener((preference, o) -> {
            setFolderShadeLevelEnable(Integer.parseInt((String) o));
            return true;
        });

        mFolderColumns.setOnPreferenceChangeListener(((preference, o) -> {
            setFolderWidthEnable((Integer) o);
            setFolderSpaceEnable((Integer) o);
            return true;
        }));
    }

    private void setFolderShadeLevelEnable(int i) {
        boolean isEnable = i != 0;
        mFolderShadeLevel.setVisible(isEnable);
        mFolderShadeLevel.setEnabled(isEnable);
    }

    private void setFolderWidthEnable(int columns) {
        boolean isEnable = columns > 1;
        mFolderWidth.setVisible(isEnable);
        mFolderWidth.setEnabled(isEnable);
    }

    private void setFolderSpaceEnable(int columns) {
        boolean isEnable = columns > 3;
        mFolderSpace.setVisible(isEnable);
        mFolderSpace.setEnabled(isEnable);
    }
}
