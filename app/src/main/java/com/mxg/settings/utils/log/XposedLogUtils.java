// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.utils.log;

import static com.mxg.settings.utils.log.LogManager.logLevel;

import de.robv.android.xposed.XposedBridge;


public class XposedLogUtils {
    public static void logI(String msg) {
        if (logLevel < 3) return;
        XposedBridge.log("[MxGSettings][I]: " + msg);
    }

    public static void logI(String tagOpkg, String msg) {
        if (logLevel < 3) return;
        XposedBridge.log("[MxGSettings][I][" + tagOpkg + "]: " + msg);
    }

    public static void logI(String tag, String pkg, String msg) {
        if (logLevel < 3) return;
        XposedBridge.log("[MxGSettings][I][" + pkg + "][" + tag + "]: " + msg);
    }

    public static void logW(String msg) {
        if (logLevel < 2) return;
        XposedBridge.log("[MxGSettings][W]: " + msg);
    }

    public static void logW(String tag, String pkg, String msg) {
        if (logLevel < 2) return;
        XposedBridge.log("[MxGSettings][W][" + pkg + "][" + tag + "]: " + msg);
    }

    public static void logW(String tag, String pkg, Throwable log) {
        if (logLevel < 2) return;
        XposedBridge.log("[MxGSettings][W][" + pkg + "][" + tag + "]: " + log);
    }

    public static void logW(String tag, String pkg, String msg, Exception exp) {
        if (logLevel < 2) return;
        XposedBridge.log("[MxGSettings][W][" + pkg + "][" + tag + "]: " + msg + ", by: " + exp);
    }

    public static void logW(String tag, String pkg, String msg, Throwable log) {
        if (logLevel < 2) return;
        XposedBridge.log("[MxGSettings][W][" + pkg + "][" + tag + "]: " + msg + ", by: " + log);
    }

    public static void logW(String tag, String msg) {
        if (logLevel < 2) return;
        XposedBridge.log("[MxGSettings][W][" + tag + "]: " + msg);
    }

    public static void logW(String tag, Throwable log) {
        if (logLevel < 2) return;
        XposedBridge.log("[MxGSettings][W][" + tag + "]: " + log);
    }

    public static void logW(String tag, String msg, Exception exp) {
        if (logLevel < 2) return;
        XposedBridge.log("[MxGSettings][W][" + tag + "]: " + msg + ", by: " + exp);
    }

    public static void logE(String tag, String msg) {
        if (logLevel < 1) return;
        XposedBridge.log("[MxGSettings][E][" + tag + "]: " + msg);
    }

    public static void logE(String msg) {
        if (logLevel < 1) return;
        XposedBridge.log("[MxGSettings][E]: " + msg);
    }

    public static void logE(String tag, Throwable log) {
        if (logLevel < 1) return;
        XposedBridge.log("[MxGSettings][E][" + tag + "]: " + log);
    }

    public static void logE(String tag, String pkg, String msg) {
        if (logLevel < 1) return;
        XposedBridge.log("[MxGSettings][E][" + pkg + "][" + tag + "]: " + msg);
    }

    public static void logE(String tag, String pkg, Throwable log) {
        if (logLevel < 1) return;
        XposedBridge.log("[MxGSettings][E][" + pkg + "][" + tag + "]: " + log);
    }

    public static void logE(String tag, String pkg, Exception exp) {
        if (logLevel < 1) return;
        XposedBridge.log("[MxGSettings][E][" + pkg + "][" + tag + "]: " + exp);
    }

    public static void logE(String tag, String pkg, String msg, Throwable log) {
        if (logLevel < 1) return;
        XposedBridge.log("[MxGSettings][E][" + pkg + "][" + tag + "]: " + msg + ", by: " + log);
    }

    public static void logE(String tag, String pkg, String msg, Exception exp) {
        if (logLevel < 1) return;
        XposedBridge.log("[MxGSettings][E][" + pkg + "][" + tag + "]: " + msg + ", by: " + exp);
    }

    public static void logD(String msg) {
        if (logLevel < 4) return;
        XposedBridge.log("[MxGSettings][D]: " + msg);
    }

    public static void logD(String tag, String pkg, String msg) {
        if (logLevel < 4) return;
        XposedBridge.log("[MxGSettings][D][" + pkg + "][" + tag + "]: " + msg);
    }

}
