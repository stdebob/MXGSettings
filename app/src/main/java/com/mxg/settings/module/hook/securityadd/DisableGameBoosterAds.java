/*
 * This file is part of HyperCeiler.

 * HyperCeiler is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.

 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.

 * Copyright (C) 2023-2024 HyperCeiler Contributions
 */
package com.mxg.settings.module.hook.securityadd;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.module.base.dexkit.DexKit;
import com.mxg.settings.module.base.dexkit.IDexKit;

import org.json.JSONObject;
import org.luckypray.dexkit.DexKitBridge;
import org.luckypray.dexkit.query.FindMethod;
import org.luckypray.dexkit.query.matchers.MethodMatcher;
import org.luckypray.dexkit.result.MethodData;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;

public class DisableGameBoosterAds extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        Method method1 = (Method) DexKit.getDexKitBridge("IsInternational", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                MethodData methodData = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .usingStrings("is_international")
                        )).singleOrNull();
                return methodData.getMethodInstance(lpparam.classLoader);
            }
        });
        hookMethod(method1, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                findAndHookMethod(JSONObject.class, "put", String.class, boolean.class, new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) throws Throwable {
                        param.args[1] = true;
                    }
                });
            }
        });

        Method method2 = (Method) DexKit.getDexKitBridge("Xunyou", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                MethodData methodData = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .usingStrings("gt_xunyou_net_privacy_alter_not_show")
                        )).singleOrNull();
                return methodData.getMethodInstance(lpparam.classLoader);
            }
        });
        hookMethod(method2, new replaceHookedMethod() {
            @Override
            protected Object replace(MethodHookParam param) throws Throwable {
                return null;
            }
        });

    }
}
