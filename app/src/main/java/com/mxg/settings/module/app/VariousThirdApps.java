// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.app;

import android.content.Context;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;

import com.mxg.settings.module.base.BaseModule;
import com.mxg.settings.module.base.HookExpand;
import com.mxg.settings.module.base.tool.OtherTool;
import com.mxg.settings.module.hook.clipboard.BaiduClipboard;
import com.mxg.settings.module.hook.clipboard.SoGouClipboard;
import com.mxg.settings.module.hook.various.ClipboardList;
import com.mxg.settings.module.hook.various.UnlockIme;
import com.mxg.settings.utils.log.XposedLogUtils;

import java.util.ArrayList;
import java.util.List;

@HookExpand(pkg = "VariousThirdApps", isPad = false, tarAndroid = 33, skip = true)
public class VariousThirdApps extends BaseModule {
    String mPackageName;

    public static List<String> mAppsUsingInputMethod = new ArrayList<>();

    @Override
    public void handleLoadPackage() {
        if (mAppsUsingInputMethod.isEmpty()) {
            mAppsUsingInputMethod = getAppsUsingInputMethod(OtherTool.findContext(OtherTool.FlAG_ONLY_ANDROID));
        }
        mPackageName = mLoadPackageParam.packageName;
        initHook(new UnlockIme(), mPrefsMap.getBoolean("various_unlock_ime") && isInputMethod(mPackageName));
        initHook(new SoGouClipboard(), mPrefsMap.getBoolean("sogou_xiaomi_clipboard") &&
                ("com.sohu.inputmethod.sogou.xiaomi".equals(mPackageName) || "com.sohu.inputmethod.sogou".equals(mPackageName)));
        initHook(new BaiduClipboard(), mPrefsMap.getBoolean("sogou_xiaomi_clipboard") &&
                ("com.baidu.input".equals(mPackageName) || "com.baidu.input_mi".equals(mPackageName)));
        initHook(new ClipboardList(), mPrefsMap.getBoolean("various_phrase_clipboardlist") && isInputMethod(mPackageName));
    }

    private List<String> getAppsUsingInputMethod(Context context) {
        try {
            if (context == null) {
                XposedLogUtils.logE("getAppsUsingInputMethod", "context is null");
                return new ArrayList<>();
            }
            List<String> pkgName = new ArrayList<>();
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            List<InputMethodInfo> enabledInputMethods = inputMethodManager.getEnabledInputMethodList();
            for (InputMethodInfo inputMethodInfo : enabledInputMethods) {
                pkgName.add(inputMethodInfo.getServiceInfo().packageName);
            }
            return pkgName;
        } catch (Throwable e) {
            XposedLogUtils.logE("getAppsUsingInputMethod", "have e: " + e);
            return new ArrayList<>();
        }
    }

    private boolean isInputMethod(String pkgName) {
        if (mAppsUsingInputMethod.isEmpty()) {
            return false;
        }
        for (String inputMethod : mAppsUsingInputMethod) {
            if (inputMethod.equals(pkgName)) {
                return true;
            }
        }
        return false;
    }
}
