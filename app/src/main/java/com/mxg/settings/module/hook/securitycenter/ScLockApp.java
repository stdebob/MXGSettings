// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.module.base.dexkit.DexKit;
import com.mxg.settings.module.base.dexkit.IDexKit;

import org.luckypray.dexkit.DexKitBridge;
import org.luckypray.dexkit.query.FindClass;
import org.luckypray.dexkit.query.FindField;
import org.luckypray.dexkit.query.FindMethod;
import org.luckypray.dexkit.query.matchers.ClassMatcher;
import org.luckypray.dexkit.query.matchers.FieldMatcher;
import org.luckypray.dexkit.query.matchers.MethodMatcher;
import org.luckypray.dexkit.result.ClassData;
import org.luckypray.dexkit.result.FieldData;
import org.luckypray.dexkit.result.MethodData;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author 焕晨HChen
 */
public class ScLockApp extends BaseHook {
    boolean isListen = false;
    boolean isLock = false;

    int value = 0;

    @Override
    public void init() throws NoSuchMethodException {
        Method method = (Method) DexKit.getDexKitBridge("StartRegionSampling", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                MethodData methodData = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .declaredClass(ClassMatcher.create()
                                        .usingStrings("startRegionSampling")
                                )
                                .name("dispatchTouchEvent")
                        )).singleOrNull();
                return methodData.getMethodInstance(lpparam.classLoader);
            }
        });
        Class<?> clazz = (Class<?>) DexKit.getDexKitBridge("StartRegionSamplingClazz", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                ClassData clazzData = bridge.findClass(FindClass.create()
                        .matcher(ClassMatcher.create()
                                .usingStrings("startRegionSampling")
                        )).singleOrNull();
                return clazzData.getInstance(lpparam.classLoader);
            }
        });
        Field field = null;
        if (method == null) {
            value = 1;
            method = (Method) DexKit.getDexKitBridge("SidebarTouchListener", new IDexKit() {
                @Override
                public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                    MethodData methodData = bridge.findMethod(FindMethod.create()
                            .matcher(MethodMatcher.create()
                                    .declaredClass(ClassMatcher.create()
                                            .usingStrings("SidebarTouchListener")
                                    )
                                    .name("onTouch")
                            )).singleOrNull();
                    return methodData.getMethodInstance(lpparam.classLoader);
                }
            });
            clazz = (Class<?>) DexKit.getDexKitBridge("OnTouchClazz", new IDexKit() {
                @Override
                public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                    ClassData clazzData = bridge.findClass(FindClass.create()
                            .matcher(ClassMatcher.create()
                                    .usingStrings("onTouch: \taction = ")
                            )).singleOrNull();
                    return clazzData.getInstance(lpparam.classLoader);
                }
            });
            field = (Field) DexKit.getDexKitBridge("OnTouchField", new IDexKit() {
                @Override
                public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                    FieldData fieldData = bridge.findField(FindField.create()
                            .matcher(FieldMatcher.create()
                                    .declaredClass(ClassMatcher.create()
                                            .usingStrings("onTouch: \taction = ")
                                    )
                                    .type(View.class)
                            )).singleOrNull();
                    return fieldData.getFieldInstance(lpparam.classLoader);
                }
            });
        }
        // logE(TAG, "dispatchTouchEvent: " + methodData + " Constructor: " + data + " class: " + data.getInstance(lpparam.classLoader) + " f: " + fieldData.getFieldInstance(lpparam.classLoader));
        if (clazz == null) {
            logE(TAG, "Class is null");
            return;
        }
        if (field == null && value == 1) {
            logE(TAG, "Field is null");
            return;
        }
        // logE(TAG, "data: " + data + " fieldData: " + fieldData + " methodData: " + methodData);
        Field finalField = field;
        hookAllConstructors(clazz, new MethodHook() {
            @Override
            protected void after(MethodHookParam param) {
                Context context = null;
                if (value == 1) {
                    try {
                        if (finalField == null) {
                            logE(TAG, "finalField is null!");
                            return;
                        }
                        context = ((View) finalField.get(param.thisObject)).getContext();
                    } catch (IllegalAccessException e) {
                        logE(TAG, "getContext E: " + e);
                    }
                } else {
                    context = (Context) param.args[0];
                }
                if (context == null) {
                    logE(TAG, "Context is null");
                    return;
                }
                if (!isListen) {
                    Context finalContext = context;
                    ContentObserver contentObserver = new ContentObserver(new Handler(finalContext.getMainLooper())) {
                        @Override
                        public void onChange(boolean selfChange) {
                            isLock = getLockApp(finalContext) != -1;
                        }
                    };
                    context.getContentResolver().registerContentObserver(
                            Settings.Global.getUriFor("key_lock_app"),
                            false, contentObserver);
                    isListen = true;
                }
            }
        });

        if (method == null) {
            logE(TAG, "Method is null");
            return;
        }
        hookMethod(method,
                new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) {
                        if (isLock) {
                            param.setResult(false);
                        }
                    }
                }
        );
    }

    public static int getLockApp(Context context) {
        try {
            return Settings.Global.getInt(context.getContentResolver(), "key_lock_app");
        } catch (Settings.SettingNotFoundException e) {
            logE("LockApp", "getInt hyceiler_lock_app will set E: " + e);
        }
        return -1;
    }
}
