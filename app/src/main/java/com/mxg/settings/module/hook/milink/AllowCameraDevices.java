// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.milink;

import com.mxg.settings.module.base.BaseHook;

public class AllowCameraDevices extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.xiaomi.vtcamera.cloud.RulesConfig", "isDeviceAllowed", String.class, new MethodHook() {
                @Override
                protected void before(MethodHookParam param) {
                    param.setResult(true);
                }
            }
        );
    }
}
