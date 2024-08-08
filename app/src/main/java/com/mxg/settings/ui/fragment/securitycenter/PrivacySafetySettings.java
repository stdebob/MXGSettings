// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment.securitycenter;

import static com.hchen.hooktool.utils.SystemSDK.isPad;
import static com.mxg.settings.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;

import android.Manifest;
import android.provider.Settings;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

import com.mxg.settings.R;
import com.mxg.settings.ui.base.BaseSettingsActivity;

import moralnorm.preference.SwitchPreference;

public class PrivacySafetySettings extends SecurityCenterBaseSettings {

    SwitchPreference mAiClipboard;
    SwitchPreference mBlurLocation;
    SwitchPreference mGetNumber;
    SwitchPreference mHideXOptModeTip;

    @Override
    public int getContentResId() {
        return R.xml.security_center_privacy_safety;
    }

    @Override
    public View.OnClickListener addRestartListener() {
        return view -> ((BaseSettingsActivity) getActivity()).showRestartDialog(
                isPad() ? getResources().getString(R.string.security_center_pad) : isMoreHyperOSVersion(1f) ? getResources().getString(R.string.security_center_hyperos) : getResources().getString(R.string.security_center),
                "com.miui.securitycenter"
        );
    }

    @Override
    public void initPrefs() {
        mBlurLocation = findPreference("prefs_key_security_center_blur_location");
        mAiClipboard = findPreference("prefs_key_security_center_ai_clipboard");
        mGetNumber = findPreference("prefs_key_security_center_get_number");
        mHideXOptModeTip = findPreference("prefs_key_security_center_hide_xopt_mode_tip");

        if (isMoreHyperOSVersion(1f)) {
            mBlurLocation.setVisible(false);
            mAiClipboard.setVisible(false);
            mGetNumber.setVisible(false);
        } else {
            mHideXOptModeTip.setVisible(false);
        }

        int permission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_SECURE_SETTINGS);
        if (permission != PermissionChecker.PERMISSION_GRANTED) {
            mBlurLocation.setSummary(R.string.security_center_no_permission);
            mAiClipboard.setSummary(R.string.security_center_no_permission);
            mBlurLocation.setEnabled(false);
            mAiClipboard.setEnabled(false);
        } else {
            boolean mBlurLocationEnable = Settings.Secure.getInt(getContext().getContentResolver(), "mi_lab_blur_location_enable", 0) == 1;
            boolean mAiClipboardEnable = Settings.Secure.getInt(getContext().getContentResolver(), "mi_lab_ai_clipboard_enable", 0) == 1;

            mBlurLocation.setChecked(mBlurLocationEnable);
            mAiClipboard.setChecked(mAiClipboardEnable);
        }

        boolean mBlurLocationEnable = Settings.Secure.getInt(getContext().getContentResolver(), "mi_lab_blur_location_enable", 0) == 1;
        boolean mAiClipboardEnable = Settings.Secure.getInt(getContext().getContentResolver(), "mi_lab_ai_clipboard_enable", 0) == 1;

        mBlurLocation.setChecked(mBlurLocationEnable);
        mAiClipboard.setChecked(mAiClipboardEnable);

        mBlurLocation.setOnPreferenceChangeListener((preference, o) -> {
            Settings.Secure.putInt(getContext().getContentResolver(), "mi_lab_blur_location_enable", (Boolean) o ? 1 : 0);
            return true;
        });

        mAiClipboard.setOnPreferenceChangeListener((preference, o) -> {
            Settings.Secure.putInt(getContext().getContentResolver(), "mi_lab_ai_clipboard_enable", (Boolean) o ? 1 : 0);
            return true;
        });
    }
}
