// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
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
