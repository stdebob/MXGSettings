// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.hook.telecom.ScamReminderBypass;

@HookExpand(pkg = "com.android.server.telecom", isPad = false, tarAndroid = 33)
public class Telecom extends BaseModule {
    @Override
    public void handleLoadPackage() {
        initHook(new ScamReminderBypass(), mPrefsMap.getBoolean("scam_reminder_bypass"));
    }
}
