// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.camera;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

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

import de.robv.android.xposed.XposedHelpers;

public class MaxScreenBrightness extends BaseHook {

    @Override
    public void init() throws NoSuchMethodException {

        Method method = (Method) DexKit.getDexKitBridge("GetHaloBrightness", new IDexKit() {
            @Override
            public AnnotatedElement dexkit(DexKitBridge bridge) throws ReflectiveOperationException {
                MethodData methodData = bridge.findMethod(FindMethod.create()
                        .matcher(MethodMatcher.create()
                                .usingNumbers(0, 8208, -1.0f, 256, 204)
                        )
                ).singleOrThrow(() -> new IllegalStateException("MaxScreenBrightness: Cannot found getHaloBrightness()"));
                return methodData.getMethodInstance(lpparam.classLoader);
            }
        });
        
        logD(TAG, lpparam.packageName, "getHaloBrightness() method is " + method);
        hookMethod(method, new MethodHook() {
            @Override
            protected void after(MethodHookParam param) throws Throwable {
                Activity activity = (Activity) XposedHelpers.callMethod(param.thisObject, "getActivity");
                setScreenBrightnessToMax(activity);
            }

        });

        findAndHookMethod(Window.class, "setAttributes", WindowManager.LayoutParams.class, new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) param.args[0];
                layoutParams.screenBrightness = 1.0f;
                param.args[0] = layoutParams;
            }
        });


        findAndHookMethod("com.android.camera.ActivityBase", "onCreate", Bundle.class, new MethodHook() {
            @Override
            protected void after(MethodHookParam param) throws Throwable {
                Activity activity = (Activity) param.thisObject;
                setScreenBrightnessToMax(activity);
            }
        });

        findAndHookMethod("com.android.camera.ActivityBase", "onStart", new MethodHook() {
            @Override
            protected void after(MethodHookParam param) throws Throwable {
                Activity activity = (Activity) param.thisObject;
                setScreenBrightnessToMax(activity);
            }
        });

        findAndHookMethod("com.android.camera.ActivityBase", "onRestart", new MethodHook() {
            @Override
            protected void after(MethodHookParam param) throws Throwable {
                Activity activity = (Activity) param.thisObject;
                setScreenBrightnessToMax(activity);
            }
        });

        findAndHookMethod("com.android.camera.ActivityBase", "onResume", new MethodHook() {
            @Override
            protected void after(MethodHookParam param) throws Throwable {
                Activity activity = (Activity) param.thisObject;
                setScreenBrightnessToMax(activity);
            }
        });

    }

    private void setScreenBrightnessToMax(Activity activity) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL;
        window.setAttributes(layoutParams);

    }

}
