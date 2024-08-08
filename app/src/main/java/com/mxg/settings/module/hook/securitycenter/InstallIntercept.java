// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.module.base.dexkit.DexKit;
import com.mxg.settings.module.base.dexkit.IDexKit;

import org.luckypray.dexkit.DexKitBridge;
import org.luckypray.dexkit.query.FindMethod;
import org.luckypray.dexkit.query.matchers.MethodMatcher;
import org.luckypray.dexkit.result.MethodData;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

public class InstallIntercept extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        long stime = System.currentTimeMillis();
        Method method = (Method) DexKit.getDexKitBridge("install", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) {
                MethodData methodData = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .usingStrings("permcenter_install_intercept_enabled")
                                .returnType(boolean.class)
                        )).singleOrNull();
                try {
                    Method method = methodData.getMethodInstance(lpparam.classLoader);
                    logE(TAG, "new: " + method);
                    return method;
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        long etime = System.currentTimeMillis();
        logE(TAG, "time: " + (etime - stime));
        logE(TAG, "find: " + method);

        hookMethod(method,
                new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) {
                        param.setResult(false);
                    }
                }
        );
    }
}
