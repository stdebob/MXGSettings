// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.navigation;

import android.content.res.Configuration;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;

import com.hchen.hooktool.callback.IAction;
import com.hchen.hooktool.tool.ParamTool;
import com.mxg.settings.module.base.BaseTool;

public class HideNavigationBar extends BaseTool {
    @Override
    public void doHook() {
        hcHook.findClass("rc", "com.miui.home.recents.views.RecentsContainer")
                .getMethod("showLandscapeOverviewGestureView", boolean.class)
                .hook(new IAction() {
                    @Override
                    public void before(ParamTool param) {
                        param.first(false);
                    }
                });

        hcHook.findClass("nsv", "com.miui.home.recents.NavStubView")
                .getMethod("isMistakeTouch")
                .hook(new IAction() {
                    @Override
                    public void before(ParamTool param) {
                        View navView = param.thisObject();
                        boolean setting = Settings.Global.getInt(navView.getContext().getContentResolver(), "show_mistake_touch_toast", 1) == 1;
                        boolean misTouch = param.callMethod("isLandScapeActually");
                        param.setResult(misTouch && setting);
                    }
                })
                .getMethod("onPointerEvent", MotionEvent.class)
                .hook(new IAction() {
                    @Override
                    public void before(ParamTool param) {
                        boolean mIsInFsMode = param.getField("mIsInFsMode");
                        MotionEvent motionEvent = param.first();
                        if (!mIsInFsMode) {
                            if (motionEvent.getAction() == 0) {
                                param.setField("mHideGestureLine", true);
                            }
                        }
                    }
                })
                .getMethod("updateScreenSize")
                .hook(new IAction() {
                    @Override
                    public void before(ParamTool param) {
                        param.setField("mHideGestureLine", false);
                    }
                })
                .getMethod("onConfigurationChanged", Configuration.class)
                .hook(new IAction() {
                    @Override
                    public void before(ParamTool param) {
                        param.setField("mHideGestureLine", false);
                    }
                });
    }
}
