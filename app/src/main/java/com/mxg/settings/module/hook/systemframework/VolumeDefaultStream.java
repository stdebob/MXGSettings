// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework;

import android.content.Context;
import android.os.Handler;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.utils.prefs.PrefType;
import com.mxg.settings.utils.prefs.PrefsChangeObserver;

import de.robv.android.xposed.XposedHelpers;

public class VolumeDefaultStream extends BaseHook {

    Class<?> mAudioService;

    @Override
    public void init() {
        mAudioService = findClassIfExists("com.android.server.audio.AudioService");

        findAndHookMethod(mAudioService, "getActiveStreamType", int.class, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) {

                Context mContext = (Context) XposedHelpers.getObjectField(param.thisObject, "mContext");
                Handler mHandler = new Handler(mContext.getMainLooper());
                new PrefsChangeObserver(mContext, mHandler, true, PrefType.String,
                        "prefs_key_system_framework_default_volume_stream", "0");

                int mDefaultVolumeStream = mPrefsMap.getStringAsInt("system_framework_default_volume_stream", 0);

                if (mDefaultVolumeStream > 0) {
                    param.setResult(mDefaultVolumeStream);
                }

            }
        });
    }
}
