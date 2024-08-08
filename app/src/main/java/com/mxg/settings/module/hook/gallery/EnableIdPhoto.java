// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.gallery;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XC_MethodHook;

public class EnableIdPhoto extends BaseHook {
    @Override
    public void init() {
        findAndHookMethod("com.miui.mediaeditor.api.MediaEditorApiHelper", "isIDPhotoAvailable", new BaseHook.MethodHook() {
            @Override
            protected void before(XC_MethodHook.MethodHookParam param) throws Throwable {
                param.setResult(true);
            }
        });
        findAndHookMethod("com.miui.gallery.domain.IDPhotoEntranceUtils", "isDeviceSupportIDPhoto", new BaseHook.MethodHook() {
            @Override
            protected void before(XC_MethodHook.MethodHookParam param) throws Throwable {
                param.setResult(true);
            }
        });
        findAndHookMethod("com.miui.gallery.domain.IDPhotoEntranceUtils", "getIdType", new BaseHook.MethodHook() {
            @Override
            protected void before(XC_MethodHook.MethodHookParam param) throws Throwable {
                param.setResult(2);
            }
        });
    }
}
