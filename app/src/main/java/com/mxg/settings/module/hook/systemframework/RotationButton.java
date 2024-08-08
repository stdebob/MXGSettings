// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework;

import android.content.Context;
import android.provider.Settings;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.utils.devicesdk.SystemSDKKt;

import de.robv.android.xposed.XposedHelpers;

public class RotationButton extends BaseHook {
    Context context;

    boolean isHyper = false;

    @Override
    public void init() throws NoSuchMethodException {
        isHyper = SystemSDKKt.isMoreHyperOSVersion(1f);
        if (isHyper) return;
        hookAllConstructors("com.android.server.wm.DisplayRotation$OrientationListener",
                new MethodHook() {
                    @Override
                    protected void after(MethodHookParam param) {
                        Object arg = param.args[1];
                        if (arg instanceof Context) {
                            context = (Context) param.args[1];
                        }
                    }
                }
        );

        findAndHookMethod("com.android.server.wm.DisplayRotation$OrientationListener",
                "onProposedRotationChanged", int.class,
                new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) {
                        int rotation = (int) param.args[0];
                        Object displayRotation = XposedHelpers.getObjectField(param.thisObject, "this$0");
                        int mCurrentAppOrientation = XposedHelpers.getIntField(displayRotation, "mCurrentAppOrientation");
                        boolean result = (boolean) XposedHelpers.callMethod(displayRotation, "isRotationChoicePossible",
                                mCurrentAppOrientation);
                        if (result) {
                            if (context == null) {
                                context = (Context) XposedHelpers.getObjectField(displayRotation, "mContext");
                                if (context == null) {
                                    logE(TAG, "context can't is null!!!");
                                    return;
                                }
                            }
                            boolean isValid = (boolean) XposedHelpers.callMethod(displayRotation, "isValidRotationChoice", rotation);
                            setData(context, rotation + "," + isValid);
                        }
                    }
                }
        );


        /*findAndHookMethod("com.android.server.wm.DisplayRotation",
                "isRotationChoicePossible", int.class,
                new MethodHook() {
                    @Override
                    protected void after(MethodHookParam param) throws Throwable {
                        super.after(param);
                        // int orientation = (int) param.args[0];
                        // boolean result = (boolean) param.getResult();
                        // if (!result && (orientation == 1 || orientation == -1)) {
                        //     param.setResult(true);
                        // }
                    }
                }
        );*/
    }

    private void setData(Context context, String value) {
        Settings.System.putString(context.getContentResolver(), "rotation_button_data", value);
    }
}
