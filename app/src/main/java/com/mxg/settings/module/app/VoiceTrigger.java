// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_

package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.voicetrigger.BypassUDKWordLegalCheck;

@HookExpand(pkg = "com.miui.voicetrigger", isPad = false, tarAndroid = 33)
public class VoiceTrigger extends BaseModule {

    @Override
    public void handleLoadPackage() {
        initHook(BypassUDKWordLegalCheck.INSTANCE, mPrefsMap.getBoolean("bypass_voicetrigger_udk_legalcheck"));
    }
}
