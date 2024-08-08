// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter.sidebar.video;

import com.github.kyuubiran.ezxhelper.HookFactory;
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

import de.robv.android.xposed.XC_MethodHook;

public class VideoDolbyOpen extends BaseHook {
    @Override
    public void init() {
        // try {
        //     findClassIfExists("com.miui.gamebooster.service.DockWindowManagerService").getDeclaredMethod("N");
        //     findAndHookMethod("com.miui.gamebooster.service.DockWindowManagerService", "N", new MethodHook() {
        //         @Override
        //         protected void before(MethodHookParam param) {
        //             logI("Hook N");
        //             param.setResult(null);
        //         }
        //     });
        // } catch (NoSuchMethodException e) {
        //     logI("Don't Find DockWindowManagerService$N");
        // }

        // 查找类
        // ClassData data = DexKit.INSTANCE.getDexKitBridge().findClass(FindClass.create()
        //     .searchPackages("com.miui.gamebooster.service")
        //     .matcher(ClassMatcher.create()
        //         .className("com.miui.gamebooster.service.DockWindowManagerService")
        //     )
        // ).singleOrThrow(() -> new IllegalStateException("VideoDolbyOpen: No class found ClassData"));
        // // 类加入列表
        // List<ClassData> list = Collections.singletonList(data);

        // 查找方法
        Method method = (Method) DexKit.getDexKitBridge("Dolby", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                MethodData methodData = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .declaredClass(ClassMatcher.create()
                                        .usingStrings("checkMiGamePermission error"))
                                .usingStrings("dolby")
                        )).singleOrNull();
                return methodData.getMethodInstance(lpparam.classLoader);
            }
        });
        HookFactory.createMethodHook(method, hookFactory -> hookFactory.before(
                methodHookParam -> methodHookParam.setResult(null)
        ));
    }
}
