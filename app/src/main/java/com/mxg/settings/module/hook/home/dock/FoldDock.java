// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.dock;

import android.content.Context;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XC_MethodReplacement;

public class FoldDock extends BaseHook {

    Class<?> mHotSeatsList;
    Class<?> mHotSeatsListRecentsAppProvider;

    Class<?> mDeviceConfig;
    Class<?> mApplication;

    @Override
    public void init() {
        mHotSeatsList = findClassIfExists("com.miui.home.launcher.hotseats.HotSeatsList");
        mHotSeatsListRecentsAppProvider = findClassIfExists("com.miui.home.launcher.hotseats.HotSeatsListRecentsAppProvider");
        mDeviceConfig = findClassIfExists("com.miui.home.launcher.DeviceConfig");
        mApplication = findClassIfExists("com.miui.home.launcher.Application");

        findAndHookMethod(mHotSeatsListRecentsAppProvider, "getLimitCount", XC_MethodReplacement.returnConstant(0));

        findAndHookMethod(mDeviceConfig, "getHotseatMaxCount", XC_MethodReplacement.returnConstant(5));


        findAndHookMethod("com.miui.home.launcher.hotseats.HotSeats", "initContent", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                findAndHookMethod(mDeviceConfig, "isFoldDevice",
                    XC_MethodReplacement.returnConstant(true));
            }

            @Override
            protected void after(MethodHookParam param) throws Throwable {

                findAndHookMethod(mDeviceConfig, "isFoldDevice",
                    XC_MethodReplacement.returnConstant(false));
            }
        });
        findAndHookMethod("com.miui.home.launcher.hotseats.HotSeats", "updateContentView", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                findAndHookMethod(mApplication, "isInFoldLargeScreen",
                    XC_MethodReplacement.returnConstant(true));
            }

            @Override
            protected void after(MethodHookParam param) throws Throwable {
                findAndHookMethod(mApplication, "isInFoldLargeScreen",
                    XC_MethodReplacement.returnConstant(false));
            }
        });

        findAndHookMethod("com.miui.home.launcher.allapps.LauncherMode", "isHomeSupportSearchBar", Context.class, XC_MethodReplacement.returnConstant(false));
    }
}
