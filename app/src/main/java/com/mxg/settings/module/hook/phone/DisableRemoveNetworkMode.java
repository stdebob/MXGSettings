// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.phone;

import com.mxg.settings.module.base.BaseHook;

public class DisableRemoveNetworkMode extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        hookAllMethods("com.android.phone.NetworkModeManager", "isRemoveNetworkModeSettings", new MethodHook(){
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult(false);
            }
        });
    }
}
