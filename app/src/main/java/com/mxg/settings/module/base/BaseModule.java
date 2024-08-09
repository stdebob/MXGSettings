// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.base;

import com.github.kyuubiran.ezxhelper.EzXHelper;
import com.hchen.hooktool.HCInit;
import com.mxg.settings.XposedInit;
import com.mxg.settings.module.base.dexkit.DexKit;
import com.mxg.settings.safe.CrashData;
import com.mxg.settings.utils.ContextUtils;
import com.mxg.settings.utils.Helpers;
import com.mxg.settings.utils.api.ProjectApi;
import com.mxg.settings.utils.log.LogManager;
import com.mxg.settings.utils.log.XposedLogUtils;
import com.mxg.settings.utils.prefs.PrefsMap;

import java.util.HashMap;

import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public abstract class BaseModule implements IXposedHook {

    public LoadPackageParam mLoadPackageParam = null;
    public String TAG = getClass().getSimpleName();
    public final PrefsMap<String, Object> mPrefsMap = XposedInit.mPrefsMap;
    private static HashMap<String, String> swappedMap = CrashData.swappedData();

    public void init(LoadPackageParam lpparam) {
        if (swappedMap.isEmpty()) swappedMap = CrashData.swappedData();
        if (CrashData.toPkgList(lpparam.packageName)) {
            XposedLogUtils.logI(TAG, "进入安全模式: " + lpparam.packageName);
            return;
        }
        EzXHelper.initHandleLoadPackage(lpparam);
        EzXHelper.setLogTag(TAG);
        EzXHelper.setToastTag(TAG);
        HCInit.setTAG("MxGSettings");
        HCInit.setLogLevel(LogManager.getLogLevel());
        HCInit.initLoadPackageParam(lpparam);
        // 把模块资源加载到目标应用
        try {
            if (!ProjectApi.mAppModulePkg.equals(lpparam.packageName)) {
                ContextUtils.getWaitContext(
                        context -> {
                            if (context != null) {
                                // try {
                                //     Handler handler = new Handler(context.getMainLooper());
                                //     BaseXposedInit.mResHook.putHandler(handler);
                                // } catch (Throwable e) {
                                // }
                                // EzXHelper.initAppContext(context, false);
                                BaseXposedInit.mResHook.loadModuleRes(context);
                                // mResHook.loadModuleRes(context);
                            }
                        }, "android".equals(lpparam.packageName));
            }
        } catch (Throwable e) {
            XposedLogUtils.logE(TAG, "get context failed!" + e);
        }
        mLoadPackageParam = lpparam;
        DexKit dexKit = new DexKit(lpparam, TAG);
        initZygote();
        handleLoadPackage();
        if (dexKit.isInit) {
            dexKit.close();
            // XposedLogUtils.logE(TAG, "close dexkit s: " + lpparam.packageName);
        }
    }

    @Override
    public void initZygote() {
    }

    /*public void initHook(BaseHook baseHook) {
        if (baseHook.isLoad()) {
            baseHook.onCreate(mLoadPackageParam);
        }
    }*/

    public void initHook(BaseHook baseHook) {
        initHook(baseHook, true);
    }

    public void initHook(BaseHook baseHook, boolean isInit) {
        if (isInit) {
            baseHook.onCreate(mLoadPackageParam);
        }
    }

    public void initHook(BaseHook baseHook, boolean isInit, String versionName) {
        initHook(baseHook, isInit, versionName, -1, -1);
    }

    public void initHook(BaseHook baseHook, boolean isInit, String versionName, int versionCodeStart, int versionCodeEnd) {
        if (isInit) {
            String mVName = Helpers.getPackageVersionName(mLoadPackageParam);
            if (mVName == null) return;
            if (mVName.equals(versionName)) {
                initHook(baseHook, true, versionCodeStart, versionCodeEnd);
            }
        }
    }

    public void initHook(BaseHook baseHook, boolean isInit, String versionName, int versionCodes) {
        initHook(baseHook, isInit, versionName, versionCodes, -1);
    }

    public void initHook(BaseHook baseHook, boolean isInit, String versionName, int[] versionCodes) {
        for (int code : versionCodes) {
            initHook(baseHook, isInit, versionName, code, -1);
        }
    }

    public void initHook(BaseHook baseHook, boolean isInit, int versionCodes) {
        initHook(baseHook, isInit, versionCodes, -1);
    }

    public void initHook(BaseHook baseHook, boolean isInit, int[] versionCodes) {
        for (int code : versionCodes) {
            initHook(baseHook, isInit, code, -1);
        }
    }

    public void initHook(BaseHook baseHook, boolean isInit, int versionCodeStart, int versionCodeEnd) {
        if (isInit) {
            if (versionCodeStart == -1) {
                baseHook.onCreate(mLoadPackageParam);
                return;
            }
            int code = Helpers.getPackageVersionCode(mLoadPackageParam);
            if (code == versionCodeStart) {
                baseHook.onCreate(mLoadPackageParam);
            } else if (versionCodeEnd != -1) {
                if (code >= versionCodeStart && code <= versionCodeEnd) {
                    baseHook.onCreate(mLoadPackageParam);
                }
            }
        }
    }
}
