// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.framework;

import android.view.View;
import android.widget.SeekBar;

import com.mxg.settings.R;
import moralnorm.preference.SeekBarPreferenceEx;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;

public class DisplaySettings extends SettingsPreferenceFragment {

    SeekBarPreferenceEx minBrightness;
    SeekBarPreferenceEx maxBrightness;

    @Override
    public int getContentResId() {
        return R.xml.framework_display;
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity)getActivity()).showRestartSystemDialog();
    }

    @Override
    public void initPrefs() {
        maxBrightness = findPreference("pref_key_system_ui_auto_brightness_max");
        minBrightness = findPreference("pref_key_system_ui_auto_brightness_min");
        assert minBrightness != null;
        minBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser) return;
                if (maxBrightness.getValue() <= progress) maxBrightness.setValue(progress + 1);
                maxBrightness.setMinValue(progress + 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}
