// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework;

import com.mxg.settings.module.base.BaseHook;

public class AllowUntrustedTouchForU  extends BaseHook {

    //Class<?> mInputManager;

    @Override
    public void init() {
        //mInputManager = findClassIfExists("android.hardware.input.InputManager");
        //XposedHelpers.setStaticLongField(mInputManager, "BLOCK_UNTRUSTED_TOUCHES", 0x96aec7eL);
        findAndHookMethod("com.android.server.wm.WindowState", "getTouchOcclusionMode", new MethodHook(){
            @Override
            protected void after(MethodHookParam param) throws Throwable {
                param.setResult(2);
            }
        });
    }
}
