// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter.battery;

import android.util.SparseArray;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.module.base.dexkit.DexKit;
import com.mxg.settings.module.base.dexkit.IDexKit;
import com.mxg.settings.module.base.dexkit.IDexKitList;

import org.luckypray.dexkit.DexKitBridge;
import org.luckypray.dexkit.query.FindClass;
import org.luckypray.dexkit.query.FindMethod;
import org.luckypray.dexkit.query.matchers.AnnotationMatcher;
import org.luckypray.dexkit.query.matchers.AnnotationsMatcher;
import org.luckypray.dexkit.query.matchers.ClassMatcher;
import org.luckypray.dexkit.query.matchers.MethodMatcher;
import org.luckypray.dexkit.result.ClassData;
import org.luckypray.dexkit.result.ClassDataList;
import org.luckypray.dexkit.result.MethodData;
import org.luckypray.dexkit.result.MethodDataList;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.List;

public class PowerConsumptionRanking extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        List<Class<?>> clazzs = DexKit.getDexKitBridgeList("Matcher1Clazz", new IDexKitList() {
            @Override
            public List<AnnotatedElement> dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                ClassDataList clazzData = bridge.findClass(
                        FindClass.create()
                                .matcher(ClassMatcher.create()
                                        .usingStrings("%d %s %d %s")
                                )
                );
                return DexKit.toElementList(clazzData);
            }
        }).toClassList();
        List<Method> methods = DexKit.getDexKitBridgeList("MiuiVersionCode", new IDexKitList() {
            @Override
            public List<AnnotatedElement> dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                MethodDataList methodData = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .declaredClass(ClassMatcher.create()
                                        .usingStrings("ro.miui.ui.version.code"))
                                .usingNumbers(9)
                                .returnType(boolean.class)
                        )
                );
                return DexKit.toElementList(methodData);
            }
        }).toMethodList();
        
        /*ClassDataList data = bridge.findClass(
            FindClass.create()
                .matcher(ClassMatcher.create()
                    .usingStrings("%d %s %d %s")
                )
        );
        MethodDataList methodDataList = bridge.findMethod(
            FindMethod.create()
                .matcher(MethodMatcher.create()
                    .declaredClass(ClassMatcher.create()
                        .usingStrings("ro.miui.ui.version.code"))
                    .usingNumbers(9)
                    .returnType(boolean.class)
                )
        );*/
        for (Class<?> clazz : clazzs) {
            logI(TAG, lpparam.packageName, "Current hooking clazz is " + clazz);
            try {
                hookAllConstructors(clazz, new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) throws NoSuchMethodException {
                        for (Method method : methods) {
                            logI(TAG, lpparam.packageName, "Current hooking method is " + method);
                            try {
                                hookMethod(method, new MethodHook() {
                                    @Override
                                    protected void before(MethodHookParam param) throws Throwable {
                                        param.setResult(false);
                                    }
                                });
                            } catch (Exception ignore) {
                            }
                        }
                    }
                });
            } catch (Exception ignored) {
            }
        }
    }
}
