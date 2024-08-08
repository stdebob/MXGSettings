// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework;

import static de.robv.android.xposed.XposedHelpers.setStaticBooleanField;

import com.mxg.settings.module.base.BaseHook;

public class DisableMiuiLite extends BaseHook {
    @Override
    public void init() {
        findAndHookMethod("miui.os.Build", "isMiuiLiteVersion", new MethodHook() {
            @Override
            protected void before(final MethodHookParam param) throws Throwable {
                param.setResult(false);
            }
        });
        setStaticBooleanField(findClassIfExists("miui.util.DeviceLevel"), "IS_MIUI_GO_VERSION", false);
        setStaticBooleanField(findClassIfExists("miui.util.DeviceLevel"), "IS_MIUI_LITE_VERSION", false);
        setStaticBooleanField(findClassIfExists("miui.util.DeviceLevel"), "IS_MIUI_MIDDLE_VERSION", false);
    }
}
