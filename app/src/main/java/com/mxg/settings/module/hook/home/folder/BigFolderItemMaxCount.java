// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.folder;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class BigFolderItemMaxCount extends BaseHook {

    Class<?> mFolderIcon2x2;
    Class<?> mFolderIcon2x2_9;
    Class<?> mFolderIcon2x2_4;
    Class<?> mBaseFolderIconPreviewContainer2X2;

    int mRealPvChildCount;

    @Override
    public void init() {
        mFolderIcon2x2 = findClassIfExists("com.miui.home.launcher.folder.FolderIcon2x2");
        mFolderIcon2x2_9 = findClassIfExists("com.miui.home.launcher.folder.FolderIconPreviewContainer2X2_9");
        mFolderIcon2x2_4 = findClassIfExists("com.miui.home.launcher.folder.FolderIconPreviewContainer2X2_4");
        mBaseFolderIconPreviewContainer2X2 = findClassIfExists("com.miui.home.launcher.folder.BaseFolderIconPreviewContainer2X2");

        findAndHookMethod(mFolderIcon2x2_9, "preSetup2x2", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {

                mRealPvChildCount = (int) XposedHelpers.callMethod(param.thisObject, "getMRealPvChildCount");
                logI(TAG, BigFolderItemMaxCount.this.lpparam.packageName, "getMRealPvChildCount1：" + mRealPvChildCount);

                if (mRealPvChildCount < 10) {
                    XposedHelpers.callMethod(param.thisObject, "setMItemsMaxCount", 9);
                    XposedHelpers.callMethod(param.thisObject, "setMLargeIconNum", 9);
                } else {
                    XposedHelpers.callMethod(param.thisObject, "setMItemsMaxCount", 12);
                    XposedHelpers.callMethod(param.thisObject, "setMLargeIconNum", 8);
                }
            }
        });

        findAndHookMethod(mFolderIcon2x2_4, "preSetup2x2", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {

                mRealPvChildCount = (int) XposedHelpers.callMethod(param.thisObject, "getMRealPvChildCount");
                logI(TAG, BigFolderItemMaxCount.this.lpparam.packageName, "getMRealPvChildCount1：" + mRealPvChildCount);

                if (mRealPvChildCount < 5) {
                    XposedHelpers.callMethod(param.thisObject, "setMItemsMaxCount", 4);
                    XposedHelpers.callMethod(param.thisObject, "setMLargeIconNum", 4);
                } else {
                    XposedHelpers.callMethod(param.thisObject, "setMItemsMaxCount", 7);
                    XposedHelpers.callMethod(param.thisObject, "setMLargeIconNum", 3);
                }
            }
        });
    }
}
