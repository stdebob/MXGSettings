// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework;

import com.mxg.settings.module.base.BaseHook;

public class HookEntry extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        hookAllMethods(
            "com.android.server.MiuiBatteryIntelligence$BatteryNotificationListernerService",
            "isNavigationStatus",
            MethodHook.returnConstant(true)
        );
    }
}
