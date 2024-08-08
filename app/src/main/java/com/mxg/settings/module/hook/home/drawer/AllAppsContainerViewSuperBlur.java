// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.drawer;

import static com.mxg.settings.utils.devicesdk.DisplayUtils.dp2px;

import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher;

import com.hchen.hooktool.callback.IAction;
import com.hchen.hooktool.tool.ParamTool;
import com.mxg.settings.module.base.BaseTool;
import com.mxg.settings.utils.Helpers;
import com.mxg.settings.utils.blur.MiBlurUtils;
import com.mxg.settings.utils.blur.MiBlurUtilsKt;

public class AllAppsContainerViewSuperBlur extends BaseTool {
    private boolean isBlur;
    private View view;
    @Override
    public void doHook() {
        classTool.findClass("allApp", "com.miui.home.launcher.allapps.BaseAllAppsContainerView")
                .getMethod("onFinishInflate")
                .hook(new IAction() {
                    @Override
                    public void after(ParamTool param) {
                        ViewSwitcher mCategoryContainer = param.getField("mCategoryContainer");
                        RelativeLayout appsView = (RelativeLayout) mCategoryContainer.getParent();
                        FrameLayout frameLayout = new FrameLayout(mCategoryContainer.getContext());
                        view = new View(mCategoryContainer.getContext());
                        frameLayout.addView(view);
                        view.getLayoutParams().height = FrameLayout.LayoutParams.MATCH_PARENT;
                        view.getLayoutParams().width = FrameLayout.LayoutParams.MATCH_PARENT;
                        if (!isBlur) {
                            MiBlurUtils.setPassWindowBlurEnabled(view, true);
                            MiBlurUtils.setMiBackgroundBlurMode(view, 1);
                            MiBlurUtils.setMiBackgroundBlurRadius(view, mPrefsMap.getInt("drawer_background_blur_degree",
                                    200));
                            MiBlurUtils.clearMiBackgroundBlendColor(view);
                            int a;
                            if (Helpers.isDarkMode(view.getContext())) a = 100;
                            else a = 140;
                            MiBlurUtils.addMiBackgroundBlendColor(view, Color.argb(a, 0, 0, 0), 103);
                            MiBlurUtils.setMiViewBlurMode(view, 1);
                            MiBlurUtilsKt.INSTANCE.setBlurRoundRect(view, dp2px(
                                    mPrefsMap.getInt("home_drawer_blur_super_radius", 30)));
                        }
                        view.setBackgroundColor(mPrefsMap.getInt("home_drawer_blur_super_bg_color", 0));
                        isBlur = true;
                        appsView.addView(frameLayout, 0);
                    }
                });
    }
}
