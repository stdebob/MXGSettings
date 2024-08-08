// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui;

import com.mxg.settings.module.base.BaseHook;

import java.util.ArrayList;

import de.robv.android.xposed.XposedHelpers;

public class UnimportantNotification extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.android.systemui.statusbar.notification.collection.coordinator.FoldCoordinator$shadeExpansionListener$1",
                "onPanelExpansionChanged", "com.android.systemui.shade.ShadeExpansionChangeEvent",
                new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) {
                        Object FoldCoordinator = XposedHelpers.getObjectField(param.thisObject, "this$0");
                        XposedHelpers.setObjectField(FoldCoordinator, "mPendingNotifications", new ArrayList<>());
                    }
                }
        );

        try {
            findAndHookMethod("com.android.systemui.statusbar.notification.collection.coordinator.FoldCoordinator",
                    "access$shouldIgnoreEntry",
                    "com.android.systemui.statusbar.notification.collection.coordinator.FoldCoordinator",
                    "com.android.systemui.statusbar.notification.collection.NotificationEntry",
                    new MethodHook() {
                        @Override
                        protected void after(MethodHookParam param) {
                            // Object mSbn = XposedHelpers.getObjectField(param.args[1], "mSbn");
                            // String getPackageName = (String) XposedHelpers.callMethod(mSbn, "getPackageName");
                            // logE(TAG, "after: " + param.getResult() + " pkg: " + getPackageName);
                            param.setResult(true);
                        }
                    }
            );
        }catch (Throwable ignore){
            findAndHookMethod("com.android.systemui.statusbar.notification.NotificationUtil",
                    "shouldIgnoreEntry",
                    "com.android.systemui.statusbar.notification.collection.NotificationEntry",
                    new MethodHook() {
                        @Override
                        protected void after(MethodHookParam param) {
                            // Object mSbn = XposedHelpers.getObjectField(param.args[1], "mSbn");
                            // String getPackageName = (String) XposedHelpers.callMethod(mSbn, "getPackageName");
                            // logE(TAG, "after: " + param.getResult() + " pkg: " + getPackageName);
                            param.setResult(true);
                        }
                    }
            );
        }
    }
}
