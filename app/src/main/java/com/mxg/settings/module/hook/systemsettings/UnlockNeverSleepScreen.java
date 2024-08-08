// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemsettings;

import android.content.Context;
import android.util.AttributeSet;

import com.mxg.settings.module.base.BaseHook;

public class UnlockNeverSleepScreen extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookConstructor("com.android.settings.KeyguardTimeoutListPreference", Context.class, AttributeSet.class, new MethodHook(){
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                findAndHookMethod("android.os.SystemProperties", "get", String.class, new MethodHook(){
                    @Override
                    protected void before(MethodHookParam param) throws Throwable {
                        param.setResult("lcd");
                    }
                });
            }
        });
    }
}
