// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui;

import static de.robv.android.xposed.XposedHelpers.findClassIfExists;

import com.mxg.settings.R;
import com.mxg.settings.module.base.BaseXposedInit;
import com.mxg.settings.module.base.tool.HookTool;

import de.robv.android.xposed.XposedHelpers;

public class NotificationVolumeSeparateSlider {
    public static void initHideDeviceControlEntry(ClassLoader pluginLoader) {
        int notifVolumeOnResId;
        int notifVolumeOffResId;

        Class<?> mMiuiVolumeDialogImpl = findClassIfExists("com.android.systemui.miui.volume.MiuiVolumeDialogImpl", pluginLoader);

        notifVolumeOnResId = R.drawable.ic_miui_volume_notification;
        notifVolumeOffResId = R.drawable.ic_miui_volume_notification_mute;

        BaseXposedInit.mResHook.setResReplacement("miui.systemui.plugin", "dimen", "miui_volume_content_width_expanded", R.dimen.miui_volume_content_width_expanded);
        BaseXposedInit.mResHook.setResReplacement("miui.systemui.plugin", "dimen", "miui_volume_ringer_layout_width_expanded", R.dimen.miui_volume_ringer_layout_width_expanded);
        BaseXposedInit.mResHook.setResReplacement("miui.systemui.plugin", "dimen", "miui_volume_column_width_expanded", R.dimen.miui_volume_column_width_expanded);
        BaseXposedInit.mResHook.setResReplacement("miui.systemui.plugin", "dimen", "miui_volume_column_margin_horizontal_expanded", R.dimen.miui_volume_column_margin_horizontal_expanded);

        HookTool.hookAllMethods(mMiuiVolumeDialogImpl, "addColumn", new HookTool.MethodHook() {
            @Override
            protected void before(MethodHookParam param) {
                if (param.args.length != 4) return;
                int streamType = (int) param.args[0];
                if (streamType == 4) {
                    XposedHelpers.callMethod(param.thisObject, "addColumn", 5, notifVolumeOnResId, notifVolumeOffResId, true, false);
                }
            }
        });
    }
}
