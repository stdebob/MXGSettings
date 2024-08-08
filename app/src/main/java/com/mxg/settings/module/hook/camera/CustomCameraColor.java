// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_

package com.mxg.settings.module.hook.camera;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.module.base.dexkit.DexKit;
import com.mxg.settings.module.base.dexkit.IDexKit;

import org.luckypray.dexkit.DexKitBridge;
import org.luckypray.dexkit.query.FindField;
import org.luckypray.dexkit.query.FindMethod;
import org.luckypray.dexkit.query.matchers.ClassMatcher;
import org.luckypray.dexkit.query.matchers.FieldMatcher;
import org.luckypray.dexkit.query.matchers.MethodMatcher;
import org.luckypray.dexkit.result.FieldData;
import org.luckypray.dexkit.result.MethodData;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import de.robv.android.xposed.XposedHelpers;

public class CustomCameraColor extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        Method method = (Method) DexKit.getDexKitBridge("CameraColorGetter", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                MethodData methodData = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .addCaller(MethodMatcher.create().declaredClass("com.android.camera.fragment.FragmentMasterFilter"))
                                .addCaller(MethodMatcher.create().declaredClass("com.android.camera.customization.BGTintTextView"))
                                .addCaller(MethodMatcher.create().declaredClass("com.android.camera.fragment.FragmentBottomPopupTips"))
                                .addCaller(MethodMatcher.create().declaredClass("com.android.camera.fragment.aiwatermark.holder.WatermarkHolder"))
                                .addCaller(MethodMatcher.create().declaredClass("com.android.camera2.compat.theme.common.MiThemeOperationBottom"))
                                .modifiers(Modifier.STATIC)
                                .returnType(int.class)
                                .paramCount(0)
                        )).singleOrNull();
                return methodData.getMethodInstance(lpparam.classLoader);
            }
        });
        Field field = (Field) DexKit.getDexKitBridge("CameraColor", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                FieldData fieldData = bridge.findField(FindField.create()
                        .matcher(FieldMatcher.create()
                                .declaredClass(method.getDeclaringClass())
                                .type(int.class)
                        )).singleOrNull();
                return fieldData.getFieldInstance(lpparam.classLoader);
            }
        });
        XposedHelpers.setStaticIntField(field.getDeclaringClass(), field.getName(), mPrefsMap.getInt("camera_custom_theme_color_picker", -2024677));
    }
}
