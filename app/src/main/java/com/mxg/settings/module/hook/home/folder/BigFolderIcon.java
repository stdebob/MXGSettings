// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.folder;

import com.mxg.settings.module.base.BaseHook;

public class BigFolderIcon extends BaseHook {

    Class<?> mFolderIcon;

    @Override
    public void init() {

        mFolderIcon = findClassIfExists("com.miui.home.launcher.folder.FolderIcon2x2_4");

        findAndHookMethod(mFolderIcon, "getPreviewCount", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) {
                param.setResult(5);
            }
        });
    }
}
