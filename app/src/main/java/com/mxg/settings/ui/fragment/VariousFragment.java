// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.ui.fragment;

import static com.mxg.settings.utils.devicesdk.MiDeviceAppUtilsKt.isPad;

import android.os.Handler;

import androidx.annotation.NonNull;

import com.mxg.settings.R;
import com.mxg.settings.ui.fragment.base.SettingsPreferenceFragment;
import com.mxg.settings.utils.KillApp;
import com.mxg.settings.utils.ThreadPoolManager;
import com.mxg.settings.utils.prefs.PrefsUtils;

import moralnorm.preference.DropDownPreference;
import moralnorm.preference.Preference;
import moralnorm.preference.PreferenceCategory;
import moralnorm.preference.SwitchPreference;

public class VariousFragment extends SettingsPreferenceFragment
    implements Preference.OnPreferenceChangeListener {

    DropDownPreference mSuperModePreference;
    PreferenceCategory mDefault;
    SwitchPreference mClipboard;
    Preference mMipad; // 平板相关功能

    Handler handler;

    @Override
    public int getContentResId() {
        return R.xml.various;
    }

    @Override
    public void initPrefs() {
        int mode = Integer.parseInt(PrefsUtils.getSharedStringPrefs(getContext(), "prefs_key_various_super_clipboard_e", "0"));
        mSuperModePreference = findPreference("prefs_key_various_super_clipboard_e");
        mDefault = findPreference("prefs_key_various_super_clipboard_key");
        mMipad = findPreference("prefs_key_various_mipad");
        mClipboard = findPreference("prefs_key_sogou_xiaomi_clipboard");
        mMipad.setVisible(isPad());
        handler = new Handler();

        mClipboard.setOnPreferenceChangeListener((preference, o) -> {
            initKill();
            return true;
        });
        setSuperMode(mode);
        mSuperModePreference.setOnPreferenceChangeListener(this);
    }

    private void initKill() {
        ThreadPoolManager.getInstance().submit(() -> {
            handler.post(() ->
                KillApp.killApps("com.sohu.inputmethod.sogou.xiaomi",
                    "com.sohu.inputmethod.sogou"));
        });
    }

    @Override
    public boolean onPreferenceChange(@NonNull Preference preference, Object o) {
        if (preference == mSuperModePreference) {
            setSuperMode(Integer.parseInt((String) o));
        }
        return true;
    }

    private void setSuperMode(int mode) {
        mDefault.setVisible(mode == 1);
    }
}
