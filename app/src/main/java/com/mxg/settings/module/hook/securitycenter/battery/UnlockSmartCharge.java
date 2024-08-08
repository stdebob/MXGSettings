// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter.battery;

import static com.mxg.settings.module.base.tool.HookTool.MethodHook.returnConstant;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.module.base.dexkit.DexKit;
import com.mxg.settings.module.base.dexkit.IDexKitList;

import org.luckypray.dexkit.DexKitBridge;
import org.luckypray.dexkit.query.FindMethod;
import org.luckypray.dexkit.query.matchers.MethodMatcher;
import org.luckypray.dexkit.result.MethodDataList;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.List;

public class UnlockSmartCharge extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        List<Method> methods = DexKit.getDexKitBridgeList("VendorSmartChg", new IDexKitList() {
            @Override
            public List<AnnotatedElement> dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                MethodDataList methodData = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .usingStrings("persist.vendor.smartchg")
                        )
                );
                return DexKit.toElementList(methodData);
            }
        }).toMethodList();
        for (Method method : methods) {
            hookMethod(method, returnConstant(true));
        }
    }
}
