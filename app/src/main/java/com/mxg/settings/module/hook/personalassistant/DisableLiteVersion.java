// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_

package com.mxg.settings.module.hook.personalassistant;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.module.base.dexkit.DexKit;
import com.mxg.settings.module.base.dexkit.IDexKit;

import org.luckypray.dexkit.DexKitBridge;
import org.luckypray.dexkit.query.FindField;
import org.luckypray.dexkit.query.FindMethod;
import org.luckypray.dexkit.query.matchers.AnnotationMatcher;
import org.luckypray.dexkit.query.matchers.AnnotationsMatcher;
import org.luckypray.dexkit.query.matchers.FieldMatcher;
import org.luckypray.dexkit.query.matchers.MethodMatcher;
import org.luckypray.dexkit.result.FieldData;
import org.luckypray.dexkit.result.MethodData;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

import de.robv.android.xposed.XposedHelpers;

public class DisableLiteVersion extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        Method method = (Method) DexKit.getDexKitBridge("GetDeviceLevel", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                MethodData methodData = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .usingStrings("getDeviceLevel # physical-memory: ")
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
                                .addReadMethod(MethodMatcher.create()
                                        .declaredClass(findClassIfExists("com.miui.personalassistant.database.oldsettings.SettingDBManager"))
                                        .returnType(Map.class)
                                        .paramCount(0)
                                        .annotations(AnnotationsMatcher.create()
                                                .add(AnnotationMatcher.create()
                                                        .usingStrings("Ljava/lang/String;")
                                                        .usingStrings("Ljava/lang/Boolean;")
                                                )
                                        )
                                )
                                .declaredClass(method.getDeclaringClass())
                                .type(boolean.class)
                                .modifiers(Modifier.PUBLIC)
                        )).singleOrNull();
                return fieldData.getFieldInstance(lpparam.classLoader);
            }
        });
        XposedHelpers.setStaticBooleanField(field.getDeclaringClass(), field.getName(), false);
        findAndHookMethod("com.miui.personalassistant.PAApplication", "onCreate", new MethodHook(){
            @Override
            protected void after(MethodHookParam param) throws Throwable {
                XposedHelpers.setStaticBooleanField(field.getDeclaringClass(), field.getName(), false);
            }
        });
    }
}
