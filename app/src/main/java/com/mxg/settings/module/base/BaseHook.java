// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.base;

import static com.mxg.settings.utils.log.LogManager.logLevel;

import com.mxg.settings.module.base.tool.HookTool;
import com.mxg.settings.module.base.tool.ResourcesTool;

import java.io.PrintWriter;
import java.io.StringWriter;

import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public abstract class BaseHook extends HookTool {
    public String TAG = getClass().getSimpleName();
    public static final ResourcesTool mResHook = BaseXposedInit.mResHook;
    // public static final XmlTool mXmlTool = BaseXposedInit.mXmlTool;
    public static final String ACTION_PREFIX = "com.mxg.settings.module.action.";

    public boolean isLoad() {
        return false;
    }

    public abstract void init() throws NoSuchMethodException;

    public void onCreate(LoadPackageParam lpparam) {
        try {
            setLoadPackageParam(lpparam);
            init();
            if (logLevel >= 3) {
                logI(TAG, lpparam.packageName, "Hook Success.");
            }
        } catch (Throwable t) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            t.printStackTrace(printWriter);
            if (logLevel >= 1) logE(TAG, lpparam.packageName, "Hook Failed: " + stringWriter);
        }
    }

    @Override
    public void setLoadPackageParam(LoadPackageParam param) {
        lpparam = param;
    }
}
