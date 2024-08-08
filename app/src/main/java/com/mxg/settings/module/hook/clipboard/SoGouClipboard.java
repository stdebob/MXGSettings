// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.clipboard;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.module.base.dexkit.DexKit;
import com.mxg.settings.module.base.dexkit.IDexKit;

import org.luckypray.dexkit.DexKitBridge;
import org.luckypray.dexkit.query.FindMethod;
import org.luckypray.dexkit.query.matchers.ClassMatcher;
import org.luckypray.dexkit.query.matchers.MethodMatcher;
import org.luckypray.dexkit.result.MethodData;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

public class SoGouClipboard extends BaseHook {
    public boolean clipboard;

    @Override
    public void init() {
        long stime = System.currentTimeMillis();
        Method method = (Method) DexKit.getDexKitBridge("sogou", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                MethodData methodData = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .declaredClass(ClassMatcher.create()
                                        .usingStrings("sogou_clipboard_tmp"))
                                .usingNumbers("com.sohu.inputmethod.sogou.xiaomi".equals(lpparam.packageName) ? 150 : 80064)
                        )).singleOrNull();
                return methodData.getMethodInstance(lpparam.classLoader);
            }
        });
        long etime = System.currentTimeMillis();
        //logE(TAG, "代码执行时间（毫秒）: " + (etime - stime));
        // logE("find class: " + lpparam.packageName);
        // logE(TAG, "method: " + method);
        hookMethod(method, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                clipboard = true;
            }

            @Override
            protected void after(MethodHookParam param) throws Throwable {
                clipboard = false;
            }
        });
        findAndHookMethod("org.greenrobot.greendao.query.QueryBuilder",
                "list", new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) {
                        if (clipboard) {
                            param.setResult(null);

                        }
                    }
                }
        );
    }
}
