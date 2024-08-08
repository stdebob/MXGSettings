// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.powerkeeper;

import androidx.annotation.NonNull;

import com.github.kyuubiran.ezxhelper.HookFactory;
import com.github.kyuubiran.ezxhelper.ObjectUtils;
import com.github.kyuubiran.ezxhelper.interfaces.IMethodHookCallback;
import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.module.base.dexkit.DexKit;
import com.mxg.settings.module.base.dexkit.IDexKit;

import org.luckypray.dexkit.DexKitBridge;
import org.luckypray.dexkit.query.FindMethod;
import org.luckypray.dexkit.query.matchers.ClassMatcher;
import org.luckypray.dexkit.query.matchers.MethodMatcher;
import org.luckypray.dexkit.result.MethodData;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.function.Consumer;

import de.robv.android.xposed.XC_MethodHook;

public class PreventBatteryWitelist extends BaseHook {
    @Override
    public void init() {
        // hookAllMethods("com.miui.powerkeeper.utils.CommonAdapter", lpparam.classLoader, "addPowerSaveWhitelistApps", new MethodHook(20000) {
        //     @Override
        //     protected void before(MethodHookParam param) throws Throwable {
        //         param.setResult(null);
        //     }
        // });

        Method method = (Method) DexKit.getDexKitBridge("FucSwitch", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                MethodData methodData = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .declaredClass(ClassMatcher.create()
                                        .usingStrings("addPowerSaveWhitelistApps: "))
                                .usingStrings("addPowerSaveWhitelistApps: ")
                        )).singleOrNull();
                return methodData.getMethodInstance(lpparam.classLoader);
            }
        });
        HookFactory.createMethodHook(method, new Consumer<HookFactory>() {
            @Override
            public void accept(HookFactory hookFactory) {
                hookFactory.before(new IMethodHookCallback() {
                    @Override
                    public void onMethodHooked(@NonNull XC_MethodHook.MethodHookParam methodHookParam) {
                        String[] strArr = (String[]) methodHookParam.args[0];
                        if (strArr.length > 1) {
                            methodHookParam.setResult(null);
                        }
                    }
                });
            }
        });
    }
}
