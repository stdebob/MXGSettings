// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework;

import com.mxg.settings.module.base.BaseHook;

public class AllowUntrustedTouch extends BaseHook {

    Class<?> mInputManager;

    @Override
    public void init() {
        mInputManager = findClassIfExists("android.hardware.input.InputManager");
        hookAllMethods(mInputManager, "getBlockUntrustedTouchesMode", MethodHook.returnConstant(0));// error
    }
}
