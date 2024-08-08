// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.statusbar.icon.all;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class IconsFromSystemManager extends BaseHook {

    @Override
    public void init() {
        Class<?> statusBarIconControllerImpl = findClass("com.android.systemui.statusbar.phone.StatusBarIconControllerImpl");

        boolean successHooked = findAndHookMethodSilently(statusBarIconControllerImpl,
            "setIcon",
            String.class, findClass("com.android.internal.statusbar.StatusBarIcon"),
            new MethodHook() {
                @Override
                protected void before(MethodHookParam param) {
                    String slotName = (String) param.args[0];
                    if (checkSlot(slotName)) {
                        XposedHelpers.setObjectField(param.args[1], "visible", false);
                    }
                }
            }
        );

        if (!successHooked) {
            findAndHookMethod(statusBarIconControllerImpl,
                "setIcon",
                String.class, findClass("com.android.systemui.statusbar.phone.StatusBarIconHolder"),
                new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) {
                        String slotName = (String) param.args[0];
                        if (checkSlot(slotName)) {
                            Object statusBarIconInstance = XposedHelpers.getObjectField(param.args[1], "mIcon");
                            XposedHelpers.setObjectField(statusBarIconInstance, "visible", false);
                        }
                    }
                });
        }

    }

    public boolean checkSlot(String slotName) {
        switch (slotName) {
            case "stealth" -> {
                return mPrefsMap.getBoolean("system_ui_status_bar_hide_icon_stealth");
            }
            case "mute" -> {
                return mPrefsMap.getBoolean("system_ui_status_bar_hide_icon_mute");
            }
            case "speakerphone" -> {
                return mPrefsMap.getBoolean("system_ui_status_bar_hide_icon_speakerphone");
            }
            case "call_record" -> {
                return mPrefsMap.getBoolean("system_ui_status_bar_hide_icon_call_record");
            }
            default -> {
                return false;
            }
        }
    }
}
