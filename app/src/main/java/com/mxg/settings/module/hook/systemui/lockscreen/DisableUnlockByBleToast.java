// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.lockscreen;

import android.content.Context;
import android.widget.Toast;

import com.mxg.settings.module.base.BaseHook;

import java.util.Objects;

public class DisableUnlockByBleToast extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.android.keyguard.KeyguardSecurityContainerController$2", "dismiss", boolean.class, int.class, boolean.class, "com.android.keyguard.KeyguardSecurityModel$SecurityMode", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                findAndHookMethod(Toast.class, "makeText", Context.class, int.class, int.class, new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) throws Throwable {
                        String resName = ((Context) param.args[0]).getResources().getResourceName((int) param.args[1]);
                        logD(TAG, lpparam.packageName, resName);
                        if (Objects.equals(resName, "com.android.systemui:string/miui_keyguard_ble_unlock_succeed_msg"))
                            findAndHookMethod(Toast.class, "show", new MethodHook() {
                                @Override
                                protected void before(MethodHookParam param) throws Throwable {
                                    param.setResult(null);
                                }
                            });
                    }
                });
            }
        });
        findAndHookMethod("com.android.keyguard.MiuiBleUnlockHelper", "tryUnlockByBle", new MethodHook(){
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                findAndHookMethod(Toast.class, "makeText", Context.class, int.class, int.class, new MethodHook(){
                    @Override
                    protected void before(MethodHookParam param) throws Throwable {
                        String resName = ((Context) param.args[0]).getResources().getResourceName((int) param.args[1]);
                        logD(TAG, lpparam.packageName, resName);
                        if (Objects.equals(resName, "com.android.systemui:string/miui_keyguard_ble_unlock_succeed_msg"))
                            findAndHookMethod(Toast.class, "show", new MethodHook() {
                                @Override
                                protected void before(MethodHookParam param) throws Throwable {
                                    param.setResult(null);
                                }
                            });
                    }
                });
            }
        });

    }
}
