// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.systemui.statusbar;

import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isAndroidVersion;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isHyperOSVersion;

import android.view.View;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;
import com.mxg.settings.utils.prefs.PrefsUtils;

import moralnorm.preference.DropDownPreference;
import moralnorm.preference.Preference;
import moralnorm.preference.SeekBarPreferenceEx;
import moralnorm.preference.SwitchPreference;

public class NetworkSpeedIndicatorSettings extends SettingsPreferenceFragment
    implements Preference.OnPreferenceChangeListener {

    SeekBarPreferenceEx mNetworkSpeedWidth; // 固定宽度
    SeekBarPreferenceEx mNetworkSpeedSpacing; // 网速间间距
    SwitchPreference mNetworkSwapIcon;
    SwitchPreference mNetworkSpeedSeparator;
    SwitchPreference mNetworkAllHide;
    DropDownPreference mNetworkAlign;
    DropDownPreference mNetworkStyle;
    DropDownPreference mNetworkIcon;

    @Override
    public int getContentResId() {
        return R.xml.system_ui_status_bar_network_speed_indicator;
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity)getActivity()).showRestartDialog(
            getResources().getString(R.string.system_ui),
            "com.android.systemui"
        );
    }

    @Override
    public void initPrefs() {
        int mNetworkMode = Integer.parseInt(PrefsUtils.getSharedStringPrefs(getContext(), "prefs_key_system_ui_statusbar_network_speed_style", "0"));
        mNetworkSpeedWidth = findPreference("prefs_key_system_ui_statusbar_network_speed_fixedcontent_width");
        mNetworkStyle = findPreference("prefs_key_system_ui_statusbar_network_speed_style");
        mNetworkAlign = findPreference("prefs_key_system_ui_statusbar_network_speed_align");
        mNetworkIcon = findPreference("prefs_key_system_ui_statusbar_network_speed_icon");
        mNetworkAllHide = findPreference("prefs_key_system_ui_statusbar_network_speed_hide_all");
        mNetworkSwapIcon = findPreference("prefs_key_system_ui_statusbar_network_speed_swap_places");
        mNetworkSpeedSeparator = findPreference("prefs_key_system_ui_status_bar_no_netspeed_separator");
        mNetworkSpeedSpacing = findPreference("prefs_key_system_ui_statusbar_network_speed_spacing_margin");
        mNetworkSpeedWidth.setVisible(!isAndroidVersion(30));
        mNetworkSpeedSeparator.setVisible(!isHyperOSVersion(1f));

        setNetworkMode(mNetworkMode);
        mNetworkStyle.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        if (preference == mNetworkStyle) {
            setNetworkMode(Integer.parseInt((String) o));
        }
        return true;
    }

    private void setNetworkMode(int mode) {
        mNetworkIcon.setVisible(mode == 3 || mode == 4);
        mNetworkSwapIcon.setVisible(mode == 3 || mode == 4);
        mNetworkAllHide.setVisible(mode == 3 || mode == 4);
        mNetworkAlign.setVisible(mode == 2 || mode == 4);
        mNetworkSpeedSpacing.setVisible(mode == 2 || mode == 4);
    }
}
