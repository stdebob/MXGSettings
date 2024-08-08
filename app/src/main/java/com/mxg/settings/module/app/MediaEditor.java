// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.mediaeditor.CustomWatermark;
import com.mxg.settings.module.hook.mediaeditor.FilterManagerAll;
import com.mxg.settings.module.hook.mediaeditor.UnlockCustomPhotoFrames;
import com.mxg.settings.module.hook.mediaeditor.UnlockDisney;
import com.mxg.settings.module.hook.mediaeditor.UnlockLeicaFilter;
import com.mxg.settings.module.hook.mediaeditor.UnlockMinimumCropLimit;

import java.util.Objects;

@HookExpand(pkg = "com.miui.mediaeditor", isPad = false, tarAndroid = 33)
public class MediaEditor extends BaseModule {

    @Override
    public void handleLoadPackage() {
        // 基础
        initHook(new UnlockMinimumCropLimit(), mPrefsMap.getBoolean("mediaeditor_unlock_minimum_crop_limit"));
        initHook(FilterManagerAll.INSTANCE, mPrefsMap.getBoolean("mediaeditor_filter_manager"));
        initHook(UnlockLeicaFilter.INSTANCE, mPrefsMap.getBoolean("mediaeditor_unlock_leica_filter"));
        initHook(CustomWatermark.INSTANCE, !Objects.equals(mPrefsMap.getString("mediaeditor_custom_watermark", ""), ""));
        // AI 创作
        initHook(UnlockCustomPhotoFrames.INSTANCE, mPrefsMap.getStringAsInt("mediaeditor_unlock_custom_photo_frames", 0) != 0);
        initHook(UnlockDisney.INSTANCE, mPrefsMap.getStringAsInt("mediaeditor_unlock_disney_some_func", 0) != 0);
    }

}
