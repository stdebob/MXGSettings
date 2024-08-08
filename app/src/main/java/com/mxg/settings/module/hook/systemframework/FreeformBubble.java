// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework;

import android.content.Context;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XC_MethodReplacement;

public class FreeformBubble extends BaseHook {

    Class<?> mMiuiMultiWindowUtils;

    @Override
    public void init() {

        mMiuiMultiWindowUtils = findClassIfExists("android.util.MiuiMultiWindowUtils");

        findAndHookMethod(mMiuiMultiWindowUtils, "multiFreeFormSupported", Context.class, XC_MethodReplacement.returnConstant(true));
    }
}
