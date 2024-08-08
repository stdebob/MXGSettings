// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.various;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.mxg.settings.XposedInit;
import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.utils.blur.BlurUtils;
import com.mxg.settings.utils.devicesdk.DisplayUtils;
import com.mxg.settings.utils.log.AndroidLogUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import de.robv.android.xposed.XposedHelpers;

public class DialogGravity extends BaseHook {

    public Context mContext;
    public View mParentPanel = null;

    final Class<?> mDialogCls = XposedHelpers.findClassIfExists("miuix.appcompat.app.AlertController", lpparam.classLoader);
    final Class<?> mDialogParentPanelCls = XposedHelpers.findClassIfExists("miuix.internal.widget.DialogParentPanel", lpparam.classLoader);

    final List<Method> methodList = new LinkedList<>();

    @Override
    public void init() {

        if (mDialogCls != null) {
            boolean oldMethodFound = false;
            for (Method method : mDialogCls.getDeclaredMethods()) {
                if (method.getName().equals("setupDialogPanel")) oldMethodFound = true;
                methodList.add(method);
                AndroidLogUtils.logI(TAG, method.getName());
            }

            int mDialogGravity = XposedInit.mPrefsMap.getStringAsInt("various_dialog_gravity", 0);

            int mDialogHorizontalMargin = XposedInit.mPrefsMap.getInt("various_dialog_horizontal_margin", 0);
            int mDialogBottomMargin = XposedInit.mPrefsMap.getInt("various_dialog_bottom_margin", 0);

            if (oldMethodFound) {

                findAndHookMethod(mDialogCls, "setupDialogPanel", Configuration.class, new MethodHook() {
                    @Override
                    protected void after(MethodHookParam param) throws Throwable {
                        mParentPanel = (View) XposedHelpers.getObjectField(param.thisObject, "mParentPanel");

                        mContext = mParentPanel.getContext();

                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mParentPanel.getLayoutParams();

                        if (mDialogGravity != 0) {

                            layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT;

                            layoutParams.gravity = mDialogGravity == 1 ? Gravity.CENTER : Gravity.BOTTOM | Gravity.CENTER;

                            layoutParams.setMarginStart(mDialogHorizontalMargin == 0 ? 0 : DisplayUtils.dp2px(mDialogHorizontalMargin));
                            layoutParams.setMarginEnd(mDialogHorizontalMargin == 0 ? 0 : DisplayUtils.dp2px(mDialogHorizontalMargin));
                            layoutParams.bottomMargin = mDialogGravity == 1 ? 0 : DisplayUtils.dp2px(mDialogBottomMargin);
                        }

                        mParentPanel.setLayoutParams(layoutParams);

                        /*new BlurUtils(mParentPanel);*/
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            new BlurUtils(mParentPanel, "default");
                        }

                    }
                });

            }

            for (Method method : methodList) {
                if (Arrays.equals(method.getParameterTypes(), new Class[]{Configuration.class}) && method.getReturnType() == Void.TYPE && method.getModifiers() == 2 && method.getParameterCount() == 1) {
                    XposedHelpers.findAndHookMethod(mDialogCls, method.getName(), new MethodHook() {
                        @Override
                        protected void after(MethodHookParam param) throws Throwable {
                            Field field = XposedHelpers.findFirstFieldByExactType(mDialogCls, mDialogParentPanelCls);
                            mParentPanel = (View) field.get(param.thisObject);

                            mContext = mParentPanel.getContext();

                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mParentPanel.getLayoutParams();

                            if (mDialogGravity != 0) {
                                layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT;

                                layoutParams.gravity = mDialogGravity == 1 ? Gravity.CENTER : Gravity.BOTTOM | Gravity.CENTER;

                                layoutParams.setMarginStart(mDialogHorizontalMargin == 0 ? 0 : DisplayUtils.dp2px(mDialogHorizontalMargin));
                                layoutParams.setMarginEnd(mDialogHorizontalMargin == 0 ? 0 : DisplayUtils.dp2px(mDialogHorizontalMargin));
                                layoutParams.bottomMargin = mDialogGravity == 1 ? 0 : DisplayUtils.dp2px(mDialogBottomMargin);
                            }

                            mParentPanel.setLayoutParams(layoutParams);

                            /*new BlurUtils(mParentPanel);*/
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                new BlurUtils(mParentPanel, "default");
                            }
                        }
                    });
                }
            }
        }

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
