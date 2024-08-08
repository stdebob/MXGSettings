/*
 * This file is part of HyperCeiler.
 *
 * HyperCeiler is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2023-2024 HyperCeiler Contributions
 */

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
