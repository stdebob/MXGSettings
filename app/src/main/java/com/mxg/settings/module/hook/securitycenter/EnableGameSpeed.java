// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter;

import static com.mxg.settings.module.base.tool.OtherTool.setProp;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.module.base.dexkit.DexKit;
import com.mxg.settings.module.base.dexkit.IDexKit;

import org.luckypray.dexkit.DexKitBridge;
import org.luckypray.dexkit.query.FindMethod;
import org.luckypray.dexkit.query.matchers.MethodMatcher;
import org.luckypray.dexkit.result.MethodData;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Objects;

import de.robv.android.xposed.XC_MethodHook;

public class EnableGameSpeed extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {

        Method method1 = (Method) DexKit.getDexKitBridge("PropVoidData", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                MethodData methodData = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .usingStrings("android.os.SystemProperties", "set", "SystemPropertiesUtils", "SystemPropertiesUtils getInt:")
                                .returnType(void.class)
                        )).singleOrNull();
                return methodData.getMethodInstance(lpparam.classLoader);
            }
        });
        hookMethod(method1, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                String param0 = (String) param.args[0];
                if (Objects.equals(param0, "debug.game.video.speed")) param.args[1] = "true";
            }
        });

        Method method2 = (Method) DexKit.getDexKitBridge("PropBooleanData", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                MethodData methodData = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .usingStrings("android.os.SystemProperties", "getBoolean", "SystemPropertiesUtils", "SystemPropertiesUtils getInt:")
                                .returnType(boolean.class)
                        )).singleOrNull();
                return methodData.getMethodInstance(lpparam.classLoader);
            }
        });
        hookMethod(method2, new MethodHook() {
            @Override
            protected void before(XC_MethodHook.MethodHookParam param) throws Throwable {
                String param0 = (String) param.args[0];
                if (Objects.equals(param0, "debug.game.video.support")) param.setResult(true);
            }
        });

        Method method3 = (Method) DexKit.getDexKitBridge("IsSupport", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                MethodData methodData = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .usingStrings("debug.game.video.support")
                                .returnType(boolean.class)
                        )).singleOrNull();
                return methodData.getMethodInstance(lpparam.classLoader);
            }
        });
        hookMethod(method3, new MethodHook() {
            @Override
            protected void before(XC_MethodHook.MethodHookParam param) throws Throwable {
                param.setResult(true);
            }
        });

        Method method4 = (Method) DexKit.getDexKitBridge("OpenGameBooster", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                MethodData methodData = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .usingStrings("pref_open_game_booster")
                                .returnType(boolean.class)
                        )).singleOrNull();
                return methodData.getMethodInstance(lpparam.classLoader);
            }
        });
        hookMethod(method4, new MethodHook() {
            @Override
            protected void before(XC_MethodHook.MethodHookParam param) throws Throwable {
                param.setResult(true);
            }
        });

        Method method5 = (Method) DexKit.getDexKitBridge("Boot", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                MethodData methodData = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .usingStrings("debug.game.video.boot")
                        )).singleOrNull();
                return methodData.getMethodInstance(lpparam.classLoader);
            }
        });
        hookMethod(method5, new MethodHook() {
            @Override
            protected void before(XC_MethodHook.MethodHookParam param) throws Throwable {
                mSetProp();
                param.setResult(null);
            }
        });

        findAndHookMethod("com.miui.gamebooster.service.GameBoosterService",
            "onCreate", new MethodHook() {
                @Override
                protected void after(MethodHookParam param) {
                    mSetProp();
                }
            }
        );
    }

    public void mSetProp() {
        setProp("debug.game.video.boot", "true");
        setProp("debug.game.video.speed", "true");
    }
}
