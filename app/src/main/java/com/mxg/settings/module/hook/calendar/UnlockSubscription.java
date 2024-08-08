// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.calendar;

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
import java.util.Objects;

import de.robv.android.xposed.XC_MethodHook;

public class UnlockSubscription extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        Method method = (Method) DexKit.getDexKitBridge("CalendarApplication", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                MethodData methodData = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .declaredClass(ClassMatcher.create()
                                        .usingStrings("Cal:D:CalendarApplicationDelegate"))
                                .usingStrings("key_subscription_display", "key_import_todo", "key_chinese_almanac_pref", "key_weather_display", "key_ai_time_parse")
                                .paramCount(0)
                        )).singleOrNull();
                return methodData.getMethodInstance(lpparam.classLoader);
            }
        });
        logD(TAG, lpparam.packageName, "method is "+method);
        hookMethod(method, new MethodHook() {
            @Override
            protected void before(XC_MethodHook.MethodHookParam param) throws Throwable {
                logD(TAG, lpparam.packageName, "1");
                try {
                    findAndHookMethod(findClass("android.app.SharedPreferencesImpl$EditorImpl"), "putBoolean", String.class, boolean.class, new MethodHook() {
                        @Override
                        protected void before(MethodHookParam param) throws Throwable {
                            String param0 = (String) param.args[0];
                            if (Objects.equals(param0, "key_subscription_display") ||
                                    Objects.equals(param0, "key_import_todo") ||
                                    Objects.equals(param0, "key_chinese_almanac_pref") ||
                                    Objects.equals(param0, "key_weather_display") ||
                                    Objects.equals(param0, "key_ai_time_parse")) param.args[1] = true;
                        }
                    });
                } catch (Exception e) {
                    logE(TAG, lpparam.packageName, "Cannot hook android.app.SharedPreferencesImpl$EditorImpl.putBoolean(String, boolean)", e);
                }
            }
        });
    }
}
