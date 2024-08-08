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

package com.mxg.settings.module.hook.thememanager;

import static com.mxg.settings.module.base.tool.OtherTool.getPackageVersionCode;

import android.util.SparseArray;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.module.base.dexkit.DexKit;
import com.mxg.settings.module.base.dexkit.IDexKit;

import org.luckypray.dexkit.DexKitBridge;
import org.luckypray.dexkit.query.FindClass;
import org.luckypray.dexkit.query.FindMethod;
import org.luckypray.dexkit.query.matchers.AnnotationMatcher;
import org.luckypray.dexkit.query.matchers.AnnotationsMatcher;
import org.luckypray.dexkit.query.matchers.ClassMatcher;
import org.luckypray.dexkit.query.matchers.MethodMatcher;
import org.luckypray.dexkit.result.ClassData;
import org.luckypray.dexkit.result.MethodData;
import org.luckypray.dexkit.result.MethodDataList;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Iterator;

public class AllowDownloadMore extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        Class<?> clazz = (Class<?>) DexKit.getDexKitBridge("DownloadCounter", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                ClassData clazzData = bridge.findClass(FindClass.create()
                        .matcher(ClassMatcher.create()
                                .usingStrings("anonymous_use_resources")
                        )).singleOrNull();
                return clazzData.getInstance(lpparam.classLoader);
            }
        });

        Method method1 = (Method) DexKit.getDexKitBridge("DownloadList", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                MethodData methodData = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .declaredClass(clazz)
                                .returnType(clazz)
                        )).singleOrNull();
                return methodData.getMethodInstance(lpparam.classLoader);
            }
        });

        Method method2 = (Method) DexKit.getDexKitBridge("DownloadListSize", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                MethodData methodData1 = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .declaredClass(clazz)
                                .returnType(int.class)
                                .usingNumbers(0)
                        )).singleOrNull();
                MethodDataList methodData2 = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .declaredClass(clazz)
                                .returnType(int.class)
                        )
                );
                methodData2.remove(methodData1);
                if (methodData2.size() == 1) {
                    for (MethodData method : methodData2) {
                        return method.getMethodInstance(lpparam.classLoader);
                    }
                }
                return null;
            }
        });

        hookMethod(method1, new MethodHook() {
            @Override
            protected void after(MethodHookParam param) throws Throwable {
                hookMethod(method2, new MethodHook() {
                    @Override
                    protected void after(MethodHookParam param) throws Throwable {
                        param.setResult(1);
                    }
                });
            }
        });
    }
}
