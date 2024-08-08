// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import android.text.TextUtils;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.updater.AndroidVersionCode;
import com.mxg.settings.module.hook.updater.AutoUpdateDialog;
import com.mxg.settings.module.hook.updater.DeviceModify;
import com.mxg.settings.module.hook.updater.VabUpdate;
import com.mxg.settings.module.hook.updater.VersionCodeModify;
import com.mxg.settings.module.hook.updater.VersionCodeNew;

@HookExpand(pkg = "com.android.updater", isPad = false, tarAndroid = 33)
public class Updater extends BaseModule {

    @Override
    public void handleLoadPackage() {
        if (mPrefsMap.getBoolean("updater_enable_miui_version")) {
            if (mPrefsMap.getStringAsInt("updater_version_mode", 1) != 1) {
                initHook(VersionCodeNew.INSTANCE, true);
            } else {
                initHook(new VersionCodeModify(), !TextUtils.isEmpty(mPrefsMap.getString("various_updater_miui_version", "")));
            }
            initHook(AndroidVersionCode.INSTANCE, !TextUtils.isEmpty(mPrefsMap.getString("various_updater_android_version", "")));
            initHook(DeviceModify.INSTANCE, !TextUtils.isEmpty(mPrefsMap.getString("updater_device", "")));
        }
        initHook(new VabUpdate(), mPrefsMap.getBoolean("updater_fuck_vab"));
        initHook(AutoUpdateDialog.INSTANCE, mPrefsMap.getBoolean("updater_diable_dialog"));
    }
}
