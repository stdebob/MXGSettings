// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.controlcenter;

import static com.mxg.settings.module.base.BaseXposedInit.mPrefsMap;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import com.mxg.settings.module.base.tool.HookTool;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class CCGridForHyperOS {
    private static final float radius = (float) mPrefsMap.getInt("system_ui_control_center_rounded_rect_radius", 72);

    public static void initCCGridForHyperOS(ClassLoader classLoader) {
        Class<?> clazz = XposedHelpers.findClass("miui.systemui.controlcenter.qs.tileview.QSTileItemIconView", classLoader);
        XposedHelpers.findAndHookMethod(clazz, "getCornerRadius",
                new HookTool.MethodHook() {
                    @Override
                    protected void before(XC_MethodHook.MethodHookParam param) {
                        param.setResult((float) mPrefsMap.getInt("system_ui_control_center_rounded_rect_radius", 72));
                    }
                }
        );
        XposedHelpers.findAndHookMethod("miui.systemui.controlcenter.qs.tileview.QSTileItemIconView", classLoader, "updateIcon",
                "com.android.systemui.plugins.qs.QSTile$State", boolean.class, boolean.class,
                new HookTool.MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) {
                        XposedHelpers.callMethod(param.thisObject, "updateResources");
                    }
                }
        );

        XposedHelpers.findAndHookMethod("miui.systemui.controlcenter.qs.tileview.QSTileItemIconView", classLoader,
                "updateResources",
                new HookTool.MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) {
                        Context pluginContext = (Context) XposedHelpers.getObjectField(param.thisObject, "pluginContext");
                        int warning = pluginContext.getResources().getIdentifier("qs_background_warning", "drawable", "miui.systemui.plugin");
                        int enabled = pluginContext.getResources().getIdentifier("qs_background_enabled", "drawable", "miui.systemui.plugin");
                        int restricted = pluginContext.getResources().getIdentifier("qs_background_restricted", "drawable", "miui.systemui.plugin");
                        int disabled = pluginContext.getResources().getIdentifier("qs_background_disabled", "drawable", "miui.systemui.plugin");
                        int unavailable = pluginContext.getResources().getIdentifier("qs_background_unavailable", "drawable", "miui.systemui.plugin");
                        Drawable warningD = pluginContext.getTheme().getDrawable(warning);
                        Drawable enabledD = pluginContext.getTheme().getDrawable(enabled);
                        Drawable restrictedD = pluginContext.getTheme().getDrawable(restricted);
                        Drawable disabledD = pluginContext.getTheme().getDrawable(disabled);
                        Drawable unavailableD = pluginContext.getTheme().getDrawable(unavailable);
                        if (warningD != null) {
                            GradientDrawable warningG = (GradientDrawable) warningD;
                            warningG.setCornerRadius(radius);
                        }
                        if (enabledD != null) {
                            GradientDrawable enabledG = (GradientDrawable) enabledD;
                            enabledG.setCornerRadius(radius);
                        }
                        if (restrictedD != null) {
                            GradientDrawable restrictedG = (GradientDrawable) restrictedD;
                            restrictedG.setCornerRadius(radius);
                        }
                        if (disabledD != null) {
                            GradientDrawable disabledG = (GradientDrawable) disabledD;
                            disabledG.setCornerRadius(radius);
                        }
                        if (unavailableD != null) {
                            GradientDrawable unavailableG = (GradientDrawable) unavailableD;
                            unavailableG.setCornerRadius(radius);
                        }
                    }
                }
        );

        XposedHelpers.findAndHookMethod("miui.systemui.controlcenter.qs.tileview.QSTileItemIconView", classLoader,
                "setCornerRadius", float.class,
                new HookTool.MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) {
                        param.setResult(null);
                    }
                }
        );
    }
}
