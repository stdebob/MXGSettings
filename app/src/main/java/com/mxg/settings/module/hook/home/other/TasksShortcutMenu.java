// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.other;

import com.mxg.settings.module.base.BaseHook;

public class TasksShortcutMenu extends BaseHook {
    @Override
    public void init() {
        hookAllMethods("com.miui.home.launcher.shortcuts.SystemShortcutMenuItem$MultipleSmallWindowShortcutMenuItem", "isValid",
                 MethodHook.returnConstant(true));
    }
}
