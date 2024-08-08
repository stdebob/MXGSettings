// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemsettings;

import static de.robv.android.xposed.XposedHelpers.getStaticBooleanField;
import static de.robv.android.xposed.XposedHelpers.setStaticBooleanField;

import android.content.Context;

import com.mxg.settings.module.base.BaseHook;

public class AntiQues extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.android.settings.MiuiDeviceNameEditFragment", "onSave", new MethodHook(){
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                boolean originalValue = getStaticBooleanField(findClassIfExists("miui.os.Build"), "IS_INTERNATIONAL_BUILD");
                setStaticBooleanField(findClassIfExists("miui.os.Build"), "IS_INTERNATIONAL_BUILD", true);
                param.setObjectExtra("originalValue", originalValue);
            }
            @Override
            protected void after(MethodHookParam param) throws Throwable {
                boolean originalValue = (boolean) param.getObjectExtra("originalValue");
                setStaticBooleanField(findClassIfExists("miui.os.Build"), "IS_INTERNATIONAL_BUILD", originalValue);
            }
        });
        findAndHookMethod("com.android.settings.DeviceNameCheckManager", "getDeviceNameCheckResult", Context.class, String.class, int.class, "com.android.settings.DeviceNameCheckManager$GetResultSuccessCallback", new MethodHook(){
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                boolean originalValue = getStaticBooleanField(findClassIfExists("miui.os.Build"), "IS_INTERNATIONAL_BUILD");
                setStaticBooleanField(findClassIfExists("miui.os.Build"), "IS_INTERNATIONAL_BUILD", true);
                param.setObjectExtra("originalValue", originalValue);
            }
            @Override
            protected void after(MethodHookParam param) throws Throwable {
                boolean originalValue = (boolean) param.getObjectExtra("originalValue");
                setStaticBooleanField(findClassIfExists("miui.os.Build"), "IS_INTERNATIONAL_BUILD", originalValue);
            }
        });
        findAndHookMethod("com.android.settings.bluetooth.MiuiBTUtils", "isSupportNameComplianceCheck", Context.class, new MethodHook(){
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult(false);
            }
        });
        findAndHookMethod("com.android.settings.bluetooth.MiuiBTUtils", "isInternationalBuild", new MethodHook(){
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult(true);
            }
        });
    }
}
