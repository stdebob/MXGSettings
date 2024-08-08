// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.voiceassist.DisableChatWatermark;
import com.mxg.settings.module.hook.voiceassist.UseThirdPartyBrowser;

@HookExpand(pkg = "com.miui.voiceassist", isPad = false, tarAndroid = 33)
public class VoiceAssist extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(new UseThirdPartyBrowser(), mPrefsMap.getBoolean("content_extension_browser"));
        initHook(new DisableChatWatermark(), mPrefsMap.getBoolean("voiceassist_disable_watermark"));
    }
}
