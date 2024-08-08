// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework;

import com.mxg.settings.R;
import com.mxg.settings.module.base.BaseHook;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XC_MethodReplacement;

public class DisableFreeformBlackList extends BaseHook {

    Class<?> mTaskCls;
    Class<?> mMiuiMultiWindowAdapter;
    Class<?> mMiuiMultiWindowUtils;

    @Override
    public void init() {

        mTaskCls = findClassIfExists("com.android.server.wm.Task");
        mMiuiMultiWindowAdapter = findClassIfExists("android.util.MiuiMultiWindowAdapter");
        mMiuiMultiWindowUtils = findClassIfExists("android.util.MiuiMultiWindowUtils");

        MethodHook clearHook = new MethodHook() {
            @Override
            protected void after(MethodHookParam param) throws Throwable {
                List<String> blackList = (List<String>) param.getResult();
                if (blackList != null) blackList.clear();
                param.setResult(blackList);
            }
        };
        hookAllMethods(mMiuiMultiWindowAdapter, "getFreeformBlackList", clearHook);
        hookAllMethods(mMiuiMultiWindowAdapter, "getFreeformBlackListFromCloud", clearHook);
        hookAllMethods(mMiuiMultiWindowAdapter, "setFreeformBlackList", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                List<String> blackList = new ArrayList<String>();
                blackList.add("ab.cd.xyz");
                param.args[0] = blackList;
            }
        });

        findAndHookMethod(mMiuiMultiWindowUtils, "isForceResizeable", XC_MethodReplacement.returnConstant(true));
        findAndHookMethod(mMiuiMultiWindowUtils, "supportFreeform", XC_MethodReplacement.returnConstant(true));

        // 强制所有活动设为可以调整大小
        /*findAndHookMethod(mTaskCls, "isResizeable", XC_MethodReplacement.returnConstant(true));*/

        mResHook.setResReplacement("android", "array", "freeform_black_list", R.array.miui_freeform_black_list);
        mResHook.setResReplacement("com.miui.rom", "array", "freeform_black_list", R.array.miui_freeform_black_list);
    }
}
