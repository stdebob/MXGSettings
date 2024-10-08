// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.systemui;

import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isHyperOSVersion;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.SeekBar;

import com.mxg.settings.R;
import com.mxg.settings.prefs.RecommendPreference;
import com.mxg.settings.ui.SubPickerActivity;
import com.mxg.settings.ui.base.BaseSettingsActivity;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;
import com.mxg.settings.ui.fragment.sub.AppPicker;
import com.mxg.settings.utils.KillApp;
import com.mxg.settings.utils.ThreadPoolManager;
import com.mxg.settings.utils.devicesdk.TelephonyManager;
import com.mxg.settings.utils.log.AndroidLogUtils;
import com.mxg.settings.utils.prefs.PrefsUtils;

import moralnorm.preference.DropDownPreference;
import moralnorm.preference.Preference;
import moralnorm.preference.PreferenceCategory;
import moralnorm.preference.SeekBarPreferenceEx;
import moralnorm.preference.SwitchPreference;

public class ControlCenterSettings extends SettingsPreferenceFragment implements Preference.OnPreferenceChangeListener {

    Preference mExpandNotification;
    PreferenceCategory mMusic;
    PreferenceCategory mCard;
    SwitchPreference mNotice;
    SwitchPreference mNoticex;
    SeekBarPreferenceEx mNewCCGrid;
    SeekBarPreferenceEx mNewCCGridColumns;
    SwitchPreference mNewCCGridLabel;
    DropDownPreference mFiveG;
    DropDownPreference mBluetoothSytle;
    SwitchPreference mRoundedRect;
    SeekBarPreferenceEx mRoundedRectRadius;
    SwitchPreference mThemeBlur;
    DropDownPreference mProgressMode;
    SeekBarPreferenceEx mProgressModeThickness;

    SwitchPreference mTaplus;
    SwitchPreference mNotifrowmenu;
    RecommendPreference mRecommend;
    Handler handler;

    @Override
    public int getContentResId() {
        return R.xml.system_ui_control_center;
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity) getActivity()).showRestartDialog(
                getResources().getString(R.string.system_ui),
                "com.android.systemui"
        );
    }

    @Override
    public void initPrefs() {
        mMusic = findPreference("prefs_key_system_ui_control_center_media_control_media_custom");
        mCard = findPreference("prefs_key_system_ui_controlcenter_card");
        mExpandNotification = findPreference("prefs_key_system_ui_control_center_expand_notification");
        mNewCCGrid = findPreference("prefs_key_system_control_center_cc_rows");
        mNewCCGridColumns = findPreference("prefs_key_system_control_center_cc_columns");
        mNewCCGridLabel = findPreference("prefs_key_system_control_center_qs_tile_label");
        mNotice = findPreference("prefs_key_n_enable");
        mNoticex = findPreference("prefs_key_n_enable_fix");
        mBluetoothSytle = findPreference("prefs_key_system_ui_control_center_cc_bluetooth_tile_style");
        mFiveG = findPreference("prefs_key_system_control_center_5g_new_tile");
        mRoundedRect = findPreference("prefs_key_system_ui_control_center_rounded_rect");
        mRoundedRectRadius = findPreference("prefs_key_system_ui_control_center_rounded_rect_radius");
        mTaplus = findPreference("prefs_key_security_center_taplus");
        mThemeBlur = findPreference("prefs_key_system_ui_control_center_unlock_blur_supported");
        mNotifrowmenu = findPreference("prefs_key_system_ui_control_center_notifrowmenu");
        mProgressMode = findPreference("prefs_key_system_ui_control_center_media_control_progress_mode");
        mProgressModeThickness = findPreference("prefs_key_system_ui_control_center_media_control_progress_thickness");
        handler = new Handler();

        mExpandNotification.setOnPreferenceClickListener(
                preference -> {
                    Intent intent = new Intent(getActivity(), SubPickerActivity.class);
                    intent.putExtra("mode", AppPicker.LAUNCHER_MODE);
                    intent.putExtra("key", preference.getKey());
                    startActivity(intent);
                    return true;
                }
        );

        mTaplus.setOnPreferenceChangeListener(
                (preference, o) -> {
                    killTaplus();
                    return true;
                }
        );

        if (isMoreHyperOSVersion(1f)) {
            mNewCCGrid.setVisible(false);
            mCard.setVisible(false);
            mNewCCGridColumns.setVisible(false);
            mNewCCGridLabel.setVisible(false);
            mNotice.setVisible(false);
            mBluetoothSytle.setVisible(false);
            mNotifrowmenu.setVisible(false);
            mMusic.setVisible(true);
            mThemeBlur.setVisible(true);
            mRoundedRectRadius.setVisible(PrefsUtils.getSharedBoolPrefs(getContext(), "prefs_key_system_ui_control_center_rounded_rect", false));
        } else {
            mNewCCGrid.setVisible(true);
            mCard.setVisible(true);
            mNewCCGridColumns.setVisible(true);
            mNewCCGridLabel.setVisible(true);
            mNotice.setVisible(true);
            mBluetoothSytle.setVisible(true);
            mNotifrowmenu.setVisible(true);
            mMusic.setVisible(false);
            mThemeBlur.setVisible(false);
            mRoundedRectRadius.setVisible(false);
        }
        mFiveG.setVisible(TelephonyManager.getDefault().isFiveGCapable());
        mProgressModeThickness.setVisible(Integer.parseInt(PrefsUtils.mSharedPreferences.getString("prefs_key_system_ui_control_center_media_control_progress_mode", "0")) == 2);

        mRoundedRect.setOnPreferenceChangeListener(this);
        mProgressMode.setOnPreferenceChangeListener(this);

        ((SeekBarPreferenceEx) findPreference("prefs_key_system_control_center_old_qs_grid_columns")).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser) return;
                if (progress < 3) progress = 5;
                try {
                    Settings.Secure.putInt(requireActivity().getContentResolver(), "sysui_qqs_count", progress);
                } catch (Throwable t) {
                    AndroidLogUtils.logD("SeekBarPreferenceEx", "onProgressChanged -> system_control_center_old_qs_grid_columns", t);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        Bundle args1 = new Bundle();
        mRecommend = new RecommendPreference(getContext());
        getPreferenceScreen().addPreference(mRecommend);

        if (isMoreHyperOSVersion(1f))
            args1.putString(":settings:fragment_args_key", "prefs_key_new_clock_status");
        else args1.putString(":settings:fragment_args_key", "prefs_key_old_clock_status");
        mRecommend.addRecommendView(getString(R.string.system_ui_statusbar_clock_title),
                null,
                StatusBarSettings.class,
                args1,
                R.string.system_ui_statusbar_title
        );

    }

    public void killTaplus() {
        ThreadPoolManager.getInstance().submit(() -> handler.post(() ->
                KillApp.killApps("com.miui.contentextension")));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        if (preference == mRoundedRect) {
            setCanBeVisibleRoundedRect((Boolean) o);
        } else if (preference == mProgressMode) {
            setCanBeVisibleProgressMode(Integer.parseInt((String) o));
        }
        return true;
    }

    private void setCanBeVisibleRoundedRect(boolean mode) {
        mRoundedRectRadius.setVisible(mode && isMoreHyperOSVersion(1f));
    }

    private void setCanBeVisibleProgressMode(int mode) {
        mProgressModeThickness.setVisible(mode == 2);
    }
}
