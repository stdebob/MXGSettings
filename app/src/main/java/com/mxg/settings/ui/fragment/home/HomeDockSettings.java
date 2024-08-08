// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.home;

import static com.mxg.settings.utils.devicesdk.MiDeviceAppUtilsKt.isPad;

import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;
import com.mxg.settings.utils.prefs.PrefsUtils;

import moralnorm.preference.ColorPickerPreference;
import moralnorm.preference.DropDownPreference;
import moralnorm.preference.Preference;
import moralnorm.preference.SwitchPreference;

public class HomeDockSettings extends SettingsPreferenceFragment implements Preference.OnPreferenceChangeListener {

    SwitchPreference mDisableRecentIcon;
    Preference mDockBackgroundBlur;
    DropDownPreference mDockBackgroundBlurEnable;
    ColorPickerPreference mDockBackgroundColor;

    @Override
    public int getContentResId() {
        return R.xml.home_dock;
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
        mDisableRecentIcon = findPreference("prefs_key_home_dock_disable_recents_icon");
        mDisableRecentIcon.setVisible(isPad());
        mDockBackgroundBlur = findPreference("prefs_key_home_dock_bg_custom");
        mDockBackgroundColor = findPreference("prefs_key_home_dock_bg_color");
        int mBlurMode = Integer.parseInt(PrefsUtils.getSharedStringPrefs(getContext(), "prefs_key_home_dock_add_blur", "0"));
        mDockBackgroundBlurEnable = findPreference("prefs_key_home_dock_add_blur");
        setCanBeVisible(mBlurMode);
        mDockBackgroundBlurEnable.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        if (preference == mDockBackgroundBlurEnable) {
            setCanBeVisible(Integer.parseInt((String) o));
        }
        return true;
    }

    private void setCanBeVisible(int mode) {
        mDockBackgroundBlur.setVisible(mode == 2);
        mDockBackgroundColor.setVisible(mode != 2);
    }
}
