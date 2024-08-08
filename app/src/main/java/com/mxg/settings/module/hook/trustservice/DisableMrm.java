// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.trustservice;

import com.mxg.settings.module.base.BaseHook;

public class DisableMrm extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.xiaomi.trustservice.remoteservice.eventhandle.statusEventHandle", "initIMrmService" ,new MethodHook(){
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult(false);
            }
        });
    }
}
