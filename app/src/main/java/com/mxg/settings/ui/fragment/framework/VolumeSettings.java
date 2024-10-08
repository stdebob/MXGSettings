// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.framework;

import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;

import android.provider.Settings;
import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;

import moralnorm.preference.DropDownPreference;
import moralnorm.preference.SwitchPreference;

public class VolumeSettings extends SettingsPreferenceFragment {
    DropDownPreference mDefaultVolumeStream;
    SwitchPreference mVolumeSeparateControl;
    SwitchPreference mVolumeSeparateSlider;

    @Override
    public int getContentResId() {
        return R.xml.framework_volume;
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity)getActivity()).showRestartSystemDialog();
    }

    @Override
    public void initPrefs() {
        mDefaultVolumeStream = findPreference("prefs_key_system_framework_default_volume_stream");
        mVolumeSeparateControl = findPreference("prefs_key_system_framework_volume_separate_control");
        mVolumeSeparateSlider = findPreference("prefs_key_system_framework_volume_separate_slider");

        mVolumeSeparateControl.setVisible(!isMoreHyperOSVersion(1f));
        mVolumeSeparateSlider.setVisible(!isMoreHyperOSVersion(1f));

        mDefaultVolumeStream.setOnPreferenceChangeListener((preference, o) -> {
            Settings.Secure.putInt(getContext().getContentResolver(), "system_framework_default_volume_stream", Integer.parseInt((String) o));
            return true;
        });
    }
}
