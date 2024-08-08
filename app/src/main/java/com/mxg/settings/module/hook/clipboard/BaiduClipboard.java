// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.clipboard;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.module.base.dexkit.DexKit;
import com.mxg.settings.module.base.dexkit.IDexKit;
import com.mxg.settings.module.base.dexkit.IDexKitList;

import org.luckypray.dexkit.DexKitBridge;
import org.luckypray.dexkit.query.FindMethod;
import org.luckypray.dexkit.query.matchers.ClassMatcher;
import org.luckypray.dexkit.query.matchers.MethodMatcher;
import org.luckypray.dexkit.result.MethodData;
import org.luckypray.dexkit.result.MethodDataList;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

public class BaiduClipboard extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        Method method1 = null;
        Method method2 = null;
        List<Method> methods = null;
        if ("com.baidu.input".equals(lpparam.packageName)) {

            method1 = (Method) DexKit.getDexKitBridge("GetMaxQueryCount", new IDexKit() {
                @Override
                public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                    MethodData methodData = bridge.findMethod(
                            FindMethod.create()
                                    .matcher(
                                            MethodMatcher.create()
                                                    .declaredClass(
                                                            ClassMatcher.create()
                                                                    .usingStrings("begin to checkMaxQueryCount")
                                                    )
                                                    .returnType(int.class)
                                                    .usingStrings("clipboard.config.max_query_count")
                                    )
                    ).singleOrNull();
                    return methodData.getMethodInstance(lpparam.classLoader);
                }
            });

            method2 = (Method) DexKit.getDexKitBridge("GetMaxCount", new IDexKit() {
                @Override
                public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                    MethodData methodData = bridge.findMethod(
                            FindMethod.create()
                                    .matcher(
                                            MethodMatcher.create()
                                                    .declaredClass(
                                                            ClassMatcher.create()
                                                                    .usingStrings("begin to checkMaxQueryCount")
                                                    )
                                                    .name("getMaxCount")
                                    )
                    ).singleOrNull();
                    return methodData.getMethodInstance(lpparam.classLoader);
                }
            });

        } else if ("com.baidu.input_mi".equals(lpparam.packageName)) {

            methods = DexKit.getDexKitBridgeList("GetMaxQueryCountList", new IDexKitList() {
                @Override
                public List<AnnotatedElement> dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                    MethodDataList methodDataList = bridge.findMethod(
                            FindMethod.create()
                                    .matcher(
                                            MethodMatcher.create()
                                                    .declaredClass(
                                                            ClassMatcher.create()
                                                                    .usingStrings("clipboard.config.max_query_count")
                                                    )
                                                    .returnType(int.class)
                                    )
                    );
                    return DexKit.toElementList(methodDataList);
                }
            }).toMethodList();
        }

        if (method1 == null && method2 == null) {
            if (methods.isEmpty()) {
                logE(TAG, "list is empty!");
                return;
            }
            if (methods.size() == 1) {
                logW(TAG, "list size only one!");
            }
            if (methods.size() == 1 || methods.size() == 2) {
                for (Method method : methods) {
                    // logE(TAG, "find method: " + method.getMethodInstance(lpparam.classLoader));
                    hookMethod(method,
                        new MethodHook() {
                            @Override
                            protected void before(MethodHookParam param) {
                                param.setResult(Integer.MAX_VALUE);
                            }
                        }
                    );
                }
                return;
            }
            logE(TAG, "list size to more!");
            return;
        }

        if (method1 != null) {
            hookMethod(method1,
                new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) {
                        param.setResult(Integer.MAX_VALUE);
                    }
                }
            );
        } else {
            logE(TAG, "no find method 1");
        }

        if (method2 != null) {
            hookMethod(method2,
                new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) {
                        param.setResult(Integer.MAX_VALUE);
                    }
                }
            );
        } else {
            logE(TAG, "no find method 2");
        }
    }
}
