// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.mms;

import android.content.Context;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.module.base.dexkit.DexKit;
import com.mxg.settings.module.base.dexkit.IDexKitList;

import org.luckypray.dexkit.DexKitBridge;
import org.luckypray.dexkit.query.FindMethod;
import org.luckypray.dexkit.query.matchers.MethodMatcher;
import org.luckypray.dexkit.result.MethodDataList;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

public class DisableAd extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {

        findAndHookMethod("com.miui.smsextra.ui.BottomMenu", "allowMenuMode",
            Context.class, new MethodHook() {
                @Override
                protected void before(MethodHookParam param) throws Throwable {
                    param.setResult(false);
                }
            });

        List<Method> methods = DexKit.getDexKitBridgeList("HideButton", new IDexKitList() {
            @Override
            public List<AnnotatedElement> dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                MethodDataList methodData = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .name("setHideButton")
                        )
                );
                return DexKit.toElementList(methodData);
            }
        }).toMethodList();
        for (Method method2 : methods) {
                    if (!Modifier.isAbstract(method2.getModifiers())) {
                        hookMethod(method2, new MethodHook() {
                            @Override
                            protected void before(MethodHookParam param) throws Throwable {
                                param.args[0] = true;
                            }
                        });
                    }
        }
}
}
