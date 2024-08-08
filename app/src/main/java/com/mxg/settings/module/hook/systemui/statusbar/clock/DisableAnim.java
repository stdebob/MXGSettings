// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.statusbar.clock;

import com.mxg.settings.module.base.BaseHook;

public class DisableAnim extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.android.systemui.statusbar.policy.FakeStatusBarClockController", "onPanelStretchChanged", float.class, boolean.class, new MethodHook() {
            @Override
            protected void before(final MethodHookParam param) throws Throwable {
                param.setResult(null);
            }
        });
    }
}
