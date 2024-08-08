// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.weather;

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

import de.robv.android.xposed.XposedHelpers;

public class SetCardLightDarkMode extends BaseHook {
    private static final String METHOD_NAME = "judgeCurrentColor() mLightDarkMode : ";
    private static final MethodMatcher METHOD_MATCHER = MethodMatcher.create().usingStrings(METHOD_NAME);

    @Override
    public void init() {
        Method method = (Method) DexKit.getDexKitBridge("LightDarkMode", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                MethodData methodData = bridge.findMethod(FindMethod.create()
                        .matcher(METHOD_MATCHER)).singleOrNull();
                return methodData.getMethodInstance(lpparam.classLoader);
            }
        });
        Field field = (Field) DexKit.getDexKitBridge("LightDarkModeField", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                FieldData fieldData = bridge.findField(FindField.create()
                        .matcher(FieldMatcher.create()
                                .declaredClass(ClassMatcher.create()
                                        .usingStrings(METHOD_NAME))
                                .addWriteMethod(METHOD_MATCHER)
                                .addReadMethod(METHOD_MATCHER)
                                .type(int.class)
                        )).singleOrNull();
                return fieldData.getFieldInstance(lpparam.classLoader);
            }
        });
        hookMethod(method, new MethodHook() {
            @Override
            protected void after(MethodHookParam param) {
                XposedHelpers.setIntField(param.thisObject, field.getName(), mPrefsMap.getStringAsInt("weather_card_display_type", 0));
            }
        });
    }
}

