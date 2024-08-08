// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework;

import com.mxg.settings.module.base.BaseHook;


public class AppLinkVerify extends BaseHook {

    @Override
    public void init() {
        try {
            hookAllMethods("com.android.server.pm.verify.domain.DomainVerificationUtils", "isDomainVerificationIntent", new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) {
                        param.setResult(false);
                    }
                }
            );
        } catch (Throwable t) {
            logE(TAG, this.lpparam.packageName, t);
        }
    }
}
