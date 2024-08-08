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
import com.mxg.settings.module.hook.aiasst.AiCaptions;
import com.mxg.settings.module.hook.aiasst.DisableWatermark;
import com.mxg.settings.module.hook.aiasst.UnlockAllCaptions;

@HookExpand(pkg = "com.xiaomi.aiasst.vision", isPad = false, tarAndroid = 33)
public class AiAsst extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new AiCaptions(), mPrefsMap.getBoolean("aiasst_ai_captions"));
        initHook(new DisableWatermark(), mPrefsMap.getBoolean("aiasst_disable_watermark"));
        initHook(UnlockAllCaptions.INSTANCE, mPrefsMap.getBoolean("aiasst_all_captions"));
    }
}
