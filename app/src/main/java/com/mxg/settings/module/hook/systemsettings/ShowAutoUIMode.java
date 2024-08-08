// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemsettings;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.utils.PropUtils;

public class ShowAutoUIMode extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.android.settings.utils.SettingsFeatures",
                "shouldShowAutoUIModeSetting", new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) {
                        boolean result = PropUtils.getProp("persist.miui.auto_ui_enable", false);
                        if (result) {
                            param.setResult(true);
                        }
                    }
                }
        );
    }
}
