// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import android.text.TextUtils;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.screenshot.DeviceShellCustomize;
import com.mxg.settings.module.hook.screenshot.SaveToPictures;
import com.mxg.settings.module.hook.screenshot.UnlockMinimumCropLimit;
import com.mxg.settings.module.hook.screenshot.UnlockPrivacyMarking;
import com.mxg.settings.module.hook.various.UnlockSuperClipboard;

@HookExpand(pkg = "com.miui.screenshot", isPad = false, tarAndroid = 33)
public class ScreenShot extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new UnlockMinimumCropLimit(), mPrefsMap.getBoolean("screenshot_unlock_minimum_crop_limit"));
        initHook(SaveToPictures.INSTANCE, mPrefsMap.getBoolean("screenshot_save_to_pictures"));
        initHook(DeviceShellCustomize.INSTANCE, !TextUtils.isEmpty(mPrefsMap.getString("screenshot_device_customize", "")));
        initHook(UnlockPrivacyMarking.INSTANCE, mPrefsMap.getBoolean("screenshot_unlock_privacy_marking"));
        // 超级剪切板
        initHook(UnlockSuperClipboard.INSTANCE, mPrefsMap.getStringAsInt("various_super_clipboard_e", 0) != 0);
    }
}
