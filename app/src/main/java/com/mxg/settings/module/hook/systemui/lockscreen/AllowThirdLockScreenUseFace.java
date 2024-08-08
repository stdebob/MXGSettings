// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.lockscreen;

import com.mxg.settings.module.base.BaseHook;

public class AllowThirdLockScreenUseFace extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.android.keyguard.KeyguardUpdateMonitor", "isUnlockWithFacePossible", int.class, new MethodHook(){
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult(true);
            }
        });
        findAndHookMethod("miui.stub.MiuiStub$3", "isUnlockWithFingerprintPossible", int.class, new MethodHook(){
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult(true);
            }
        });
    }
}
