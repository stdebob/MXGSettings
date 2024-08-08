// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XC_MethodReplacement;

public class DisablePinVerifyPer72h extends BaseHook {
    @Override
    public void init() {
        findAndHookMethod("com.android.server.locksettings.LockSettingsStrongAuth", "rescheduleStrongAuthTimeoutAlarm", long.class, int.class, XC_MethodReplacement.DO_NOTHING);
    }
}
