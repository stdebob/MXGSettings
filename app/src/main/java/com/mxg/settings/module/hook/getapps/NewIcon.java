// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.getapps;


import com.mxg.settings.module.base.BaseHook;

import java.lang.reflect.Method;


public class NewIcon extends BaseHook {
    static Method isDesktopSupportOperationIcon;

    @Override
    public void init() {
       /* try {
            List<DexMethodDescriptor> result = Objects.requireNonNull(MarketDexKit.mMarketResultMethodsMap.get("DesktopSupportOperationIcon"));
            for (DexMethodDescriptor descriptor : result) {
                isDesktopSupportOperationIcon = descriptor.getMethodInstance(lpparam.classLoader);
                logI("isDesktopSupportOperationIcon method is " + isDesktopSupportOperationIcon);
                if (isDesktopSupportOperationIcon.getReturnType() == boolean.class) {
                    XposedBridge.hookMethod(isDesktopSupportOperationIcon, XC_MethodReplacement.returnConstant(false));
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        hookAllMethods("com.xiaomi.market.util.FileUtils", "ensureExternalIconDir", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) {
                param.setResult(false);
            }
        });*/
    }
}
