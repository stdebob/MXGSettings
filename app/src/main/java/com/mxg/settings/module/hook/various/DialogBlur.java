// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.various;

import android.os.Build;
import android.view.View;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.utils.blur.BlurUtils;

import de.robv.android.xposed.XposedHelpers;

public class DialogBlur extends BaseHook {

    final Class<?> mDialogCls = findClassIfExists("miuix.appcompat.app.AlertController");

    @Override
    public void init() {
        hookAllMethods(mDialogCls, "installContent", new MethodHook() {
            @Override
            protected void after(MethodHookParam param) throws Throwable {

                View mParentPanel = (View) XposedHelpers.getObjectField(param.thisObject, "mParentPanel");

                if (mParentPanel != null) {
                    /*new BlurUtils(mParentPanel);*/
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        new BlurUtils(mParentPanel, "default");
                    }
                }
            }
        });

        hookAllMethods(mDialogCls, "dismiss", new MethodHook() {
            @Override
            protected void after(MethodHookParam param) throws Throwable {
                super.after(param);
                View mParentPanel = (View) XposedHelpers.getObjectField(param.thisObject, "mParentPanel");
                mParentPanel.setVisibility(View.INVISIBLE);
            }
        });
    }
}
