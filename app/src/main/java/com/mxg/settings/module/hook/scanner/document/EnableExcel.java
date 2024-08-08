// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.scanner.document;

import com.mxg.settings.module.base.BaseHook;

public class EnableExcel extends BaseHook {
    @Override
    public void init() {
        hookAllMethods("com.xiaomi.scanner.util.SPUtils", "getFormModule", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult(true);
            }
        });
        hookAllMethods("com.xiaomi.scanner.settings.FeatureManager", "isSupportForm", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult(true);
            }
        });
        hookAllMethods("com.xiaomi.scanner.settings.FeatureManager", "isAddFormRecognitionFunction", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult(true);
            }
        });
    }
}
