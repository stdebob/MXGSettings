/*
 * This file is part of HyperCeiler.

 * HyperCeiler is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.

 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.

 * Copyright (C) 2023-2024 HyperCeiler Contributions
 */
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
