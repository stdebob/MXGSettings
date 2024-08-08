// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework;

import android.media.AudioManager;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class VolumeFirstPress extends BaseHook {

    Class<?> mVolumeController;

    @Override
    public void init() {
        mVolumeController = findClassIfExists("com.android.server.audio.AudioService$VolumeController");

        findAndHookMethod(mVolumeController, "suppressAdjustment", int.class, int.class, boolean.class, new MethodHook() {
            @Override
            protected void after(MethodHookParam param) {
                int streamType = (int) param.args[0];
                if (streamType != AudioManager.STREAM_MUSIC) return;
                boolean isMuteAdjust = (boolean) param.args[2];
                if (isMuteAdjust) return;
                Object mController = XposedHelpers.getObjectField(param.thisObject, "mController");
                if (mController == null) return;
                param.setResult(false);
            }
        });
    }
}
