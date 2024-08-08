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
