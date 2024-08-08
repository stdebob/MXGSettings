// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.screenrecorder.ForceSupportPlaybackCapture;
import com.mxg.settings.module.hook.screenrecorder.SaveToMovies;
import com.mxg.settings.module.hook.screenrecorder.ScreenRecorderConfig;
import com.mxg.settings.module.hook.screenrecorder.UnlockMoreVolumeFromNew;

@HookExpand(pkg = "com.miui.screenrecorder", isPad = false, tarAndroid = 33)
public class ScreenRecorder extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new ForceSupportPlaybackCapture(), mPrefsMap.getBoolean("screenrecorder_force_support_playback_capture"));
        initHook(UnlockMoreVolumeFromNew.INSTANCE, mPrefsMap.getBoolean("screenrecorder_more_volume"));
        initHook(new ScreenRecorderConfig(), mPrefsMap.getBoolean("screenrecorder_config"));
        initHook(SaveToMovies.INSTANCE, mPrefsMap.getBoolean("screenrecorder_save_to_movies"));
    }
}
