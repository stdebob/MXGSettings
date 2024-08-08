// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.camera;

import android.util.SparseArray;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.module.base.dexkit.DexKit;
import com.mxg.settings.module.base.dexkit.IDexKitList;

import org.luckypray.dexkit.DexKitBridge;
import org.luckypray.dexkit.query.FindMethod;
import org.luckypray.dexkit.query.matchers.AnnotationMatcher;
import org.luckypray.dexkit.query.matchers.AnnotationsMatcher;
import org.luckypray.dexkit.query.matchers.MethodMatcher;
import org.luckypray.dexkit.result.MethodDataList;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.List;

public class CustomWatermark extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        List<Method> methods = DexKit.getDexKitBridgeList("Watermark", new IDexKitList() {
            @Override
            public List<AnnotatedElement> dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                MethodDataList methodData = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .returnType(SparseArray.class)
                                .paramCount(0)
                                .annotations(AnnotationsMatcher.create()
                                        .add(AnnotationMatcher.create()
                                                .usingStrings("Ljava/lang/String;")
                                        )
                                )
                        )
                );
                return DexKit.toElementList(methodData);
            }
        }).toMethodList();
        for (Method method : methods) {
            // Method method = methodData.getMethodInstance(lpparam.classLoader);
            logD(TAG, lpparam.packageName, "Current hooking method is " + method);
            hookMethod(method, new MethodHook() {
                @Override
                protected void before(MethodHookParam param) throws Throwable {
                    SparseArray<String[]> sparseArray = new SparseArray<>(1);
                    sparseArray.put(0, new String[]{mPrefsMap.getString("camera_custom_watermark_manufacturer", "XIAOMI"), mPrefsMap.getString("camera_custom_watermark_device", "MI PHONE")});
                    param.setResult(sparseArray);
                }
            });
        }
    }
}
