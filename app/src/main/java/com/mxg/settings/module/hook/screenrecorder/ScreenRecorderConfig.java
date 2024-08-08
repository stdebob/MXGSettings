// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.screenrecorder;

import com.github.kyuubiran.ezxhelper.ObjectUtils;
import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.module.base.dexkit.DexKit;
import com.mxg.settings.module.base.dexkit.IDexKit;

import org.luckypray.dexkit.DexKitBridge;
import org.luckypray.dexkit.query.FindMethod;
import org.luckypray.dexkit.query.matchers.MethodMatcher;
import org.luckypray.dexkit.result.MethodData;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class ScreenRecorderConfig extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        Method method1 = (Method) DexKit.getDexKitBridge("Frame", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                MethodData methodData = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .usingStrings("Error when set frame value, maxValue = ")
                        )).singleOrNull();
                return methodData.getMethodInstance(lpparam.classLoader);
            }
        });
        hookMethod(method1, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.args[0] = 1200;
                param.args[1] = 1;

                Field[] fields = param.method.getDeclaringClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (Modifier.isFinal(field.getModifiers())) {
                        Object value = field.get(null);
                        if (value instanceof int[]) {
                            int[] intArray = (int[]) value;
                            if (Arrays.equals(intArray, new int[]{15, 24, 30, 48, 60, 90})) {
                                field.set(null, new int[]{15, 24, 30, 48, 60, 90, 120, 144});
                                break;
                            }
                        }
                    }
                }
            }
        });

        Method method2 = (Method) DexKit.getDexKitBridge("BitRate", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                MethodData methodData = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .usingStrings("defaultBitRate = ")
                        )).singleOrNull();
                return methodData.getMethodInstance(lpparam.classLoader);
            }
        });
        hookMethod(method2, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.args[0] = 1200;
                param.args[1] = 1;

                Field[] fields = param.method.getDeclaringClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (Modifier.isFinal(field.getModifiers())) {
                        Object value = field.get(null);
                        if (value instanceof int[]) {
                            int[] intArray = (int[]) value;
                            if (Arrays.equals(intArray, new int[]{200, 100, 50, 32, 24, 16, 8, 6, 4, 1})) {
                                field.set(null, new int[]{1200, 800, 400, 200, 100, 50, 32, 24, 16, 8, 6, 4, 1});
                                break;
                            }
                        }
                    }
                }
            }
        });
    }
}
