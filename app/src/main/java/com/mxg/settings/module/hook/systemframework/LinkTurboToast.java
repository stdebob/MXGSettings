// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework;

import com.mxg.settings.module.base.BaseHook;

public class LinkTurboToast extends BaseHook {
    @Override
    public void init() {
        findAndHookMethod("com.xiaomi.NetworkBoost.slaservice.SLAToast",
            "setLinkTurboStatus", boolean.class, new MethodHook() {
                @Override
                protected void before(MethodHookParam param) {
                    param.args[0] = false;
                }
            }
        );
    }
}
