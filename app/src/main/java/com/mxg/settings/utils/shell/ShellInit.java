// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.utils.shell;

import com.mxg.settings.callback.IResult;
import com.mxg.settings.utils.log.AndroidLogUtils;

/**
 * 本工具默认使用 Root 启动。
 * 本工具只可以在本应用使用！不可在 Hook 代码内使用！
 *
 * @author 焕晨HChen
 */
public class ShellInit {
    private final static String TAG = "ShellInit";
    private static ShellExec mShell = null;
    private static boolean lastReady = false;
    private static IResult mResult = null;

    public static void init() {
        init(null);
    }

    public static void init(IResult result) {
        try {
            if (mShell != null && !mShell.isDestroy()) {
                return;
            }
            mResult = result;
            mShell = new ShellExec(true, true, result);
            lastReady = mShell.ready();
        } catch (RuntimeException e) {
            AndroidLogUtils.logE(TAG, e);
        }
    }

    public static void destroy() {
        if (mShell != null && !mShell.isDestroy()) {
            mShell.close();
            mShell = null;
            mResult = null;
        } else if (mShell != null && mShell.isDestroy()) {
            mShell = null;
            mResult = null;
        }
    }

    public static ShellExec getShell() {
        if (mShell != null) {
            if (!mShell.isRoot()) {
                return mShell;
            }
            if (mShell.isDestroy()) {
                AndroidLogUtils.logW(TAG, "The current shell has been destroyed, please try creating it again!");
                mShell = new ShellExec(true, true, mResult);
            }
            return mShell;
        } else {
            if (lastReady) {
                AndroidLogUtils.logW(TAG, "ShellExec is null!! Attempt to rewrite creation...");
                return new ShellExec(true, true, mResult);
            } else {
                return new ShellExec(true, true, mResult);
                // throw new RuntimeException("ShellExec is null!! " +
                //     "And it seems like it has never been created successfully!");
            }
        }
    }

    public static boolean ready() {
        if (mShell != null) {
            if (!mShell.isRoot()) {
                return false;
            }
            if (mShell.isDestroy()) {
                init(mResult);
            }
            return mShell.ready();
        }
        if (lastReady) {
            init(mResult);
            return mShell.ready();
        }
        return false;
    }
}
