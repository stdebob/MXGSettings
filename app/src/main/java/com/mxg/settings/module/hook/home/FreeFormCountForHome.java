// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home;

import com.mxg.settings.module.base.BaseHook;

public class FreeFormCountForHome extends BaseHook {

    Class<?> mRecentsAndFSGesture;

    @Override
    public void init() {
        mRecentsAndFSGesture = findClassIfExists("com.miui.home.launcher.RecentsAndFSGestureUtils");

        hookAllMethods(mRecentsAndFSGesture,
            "canTaskEnterMiniSmallWindow",
            MethodHook.returnConstant(true));

        hookAllMethods(mRecentsAndFSGesture,
            "canTaskEnterSmallWindow",
            MethodHook.returnConstant(true));
    }
}
