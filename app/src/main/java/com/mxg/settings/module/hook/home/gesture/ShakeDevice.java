// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.gesture;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.utils.ShakeManager;

import de.robv.android.xposed.XposedHelpers;

public class ShakeDevice extends BaseHook {
    @Override
    public void init() {
        final String shakeMgrKey = "MIUIZER_SHAKE_MGR";

        findAndHookMethod("com.miui.home.launcher.Launcher", "onResume", new MethodHook() {
            @Override
            protected void after(final MethodHookParam param) throws Throwable {
                ShakeManager shakeMgr = (ShakeManager) XposedHelpers.getAdditionalInstanceField(param.thisObject, shakeMgrKey);
                if (shakeMgr == null) {
                    shakeMgr = new ShakeManager((Context) param.thisObject);
                    XposedHelpers.setAdditionalInstanceField(param.thisObject, shakeMgrKey, shakeMgr);
                }
                Activity launcherActivity = (Activity) param.thisObject;
                SensorManager sensorMgr = (SensorManager) launcherActivity.getSystemService(Context.SENSOR_SERVICE);
                shakeMgr.reset();
                sensorMgr.registerListener(shakeMgr, sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
            }
        });

        findAndHookMethod("com.miui.home.launcher.Launcher", "onPause", new MethodHook() {
            @Override
            protected void after(final MethodHookParam param) throws Throwable {
                if (XposedHelpers.getAdditionalInstanceField(param.thisObject, shakeMgrKey) == null) return;
                Activity launcherActivity = (Activity) param.thisObject;
                SensorManager sensorMgr = (SensorManager) launcherActivity.getSystemService(Context.SENSOR_SERVICE);
                sensorMgr.unregisterListener((ShakeManager) XposedHelpers.getAdditionalInstanceField(param.thisObject, shakeMgrKey));
            }
        });
    }
}
