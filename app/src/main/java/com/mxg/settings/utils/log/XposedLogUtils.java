// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.utils.log;

import static com.mxg.settings.utils.log.LogManager.logLevel;

import de.robv.android.xposed.XposedBridge;


public class XposedLogUtils {
    public static void logI(String msg) {
        if (logLevel < 3) return;
        XposedBridge.log("[HyperCeiler][I]: " + msg);
    }

    public static void logI(String tagOpkg, String msg) {
        if (logLevel < 3) return;
        XposedBridge.log("[HyperCeiler][I][" + tagOpkg + "]: " + msg);
    }

    public static void logI(String tag, String pkg, String msg) {
        if (logLevel < 3) return;
        XposedBridge.log("[HyperCeiler][I][" + pkg + "][" + tag + "]: " + msg);
    }

    public static void logW(String msg) {
        if (logLevel < 2) return;
        XposedBridge.log("[HyperCeiler][W]: " + msg);
    }

    public static void logW(String tag, String pkg, String msg) {
        if (logLevel < 2) return;
        XposedBridge.log("[HyperCeiler][W][" + pkg + "][" + tag + "]: " + msg);
    }

    public static void logW(String tag, String pkg, Throwable log) {
        if (logLevel < 2) return;
        XposedBridge.log("[HyperCeiler][W][" + pkg + "][" + tag + "]: " + log);
    }

    public static void logW(String tag, String pkg, String msg, Exception exp) {
        if (logLevel < 2) return;
        XposedBridge.log("[HyperCeiler][W][" + pkg + "][" + tag + "]: " + msg + ", by: " + exp);
    }

    public static void logW(String tag, String pkg, String msg, Throwable log) {
        if (logLevel < 2) return;
        XposedBridge.log("[HyperCeiler][W][" + pkg + "][" + tag + "]: " + msg + ", by: " + log);
    }

    public static void logW(String tag, String msg) {
        if (logLevel < 2) return;
        XposedBridge.log("[HyperCeiler][W][" + tag + "]: " + msg);
    }

    public static void logW(String tag, Throwable log) {
        if (logLevel < 2) return;
        XposedBridge.log("[HyperCeiler][W][" + tag + "]: " + log);
    }

    public static void logW(String tag, String msg, Exception exp) {
        if (logLevel < 2) return;
        XposedBridge.log("[HyperCeiler][W][" + tag + "]: " + msg + ", by: " + exp);
    }

    public static void logE(String tag, String msg) {
        if (logLevel < 1) return;
        XposedBridge.log("[HyperCeiler][E][" + tag + "]: " + msg);
    }

    public static void logE(String msg) {
        if (logLevel < 1) return;
        XposedBridge.log("[HyperCeiler][E]: " + msg);
    }

    public static void logE(String tag, Throwable log) {
        if (logLevel < 1) return;
        XposedBridge.log("[HyperCeiler][E][" + tag + "]: " + log);
    }

    public static void logE(String tag, String pkg, String msg) {
        if (logLevel < 1) return;
        XposedBridge.log("[HyperCeiler][E][" + pkg + "][" + tag + "]: " + msg);
    }

    public static void logE(String tag, String pkg, Throwable log) {
        if (logLevel < 1) return;
        XposedBridge.log("[HyperCeiler][E][" + pkg + "][" + tag + "]: " + log);
    }

    public static void logE(String tag, String pkg, Exception exp) {
        if (logLevel < 1) return;
        XposedBridge.log("[HyperCeiler][E][" + pkg + "][" + tag + "]: " + exp);
    }

    public static void logE(String tag, String pkg, String msg, Throwable log) {
        if (logLevel < 1) return;
        XposedBridge.log("[HyperCeiler][E][" + pkg + "][" + tag + "]: " + msg + ", by: " + log);
    }

    public static void logE(String tag, String pkg, String msg, Exception exp) {
        if (logLevel < 1) return;
        XposedBridge.log("[HyperCeiler][E][" + pkg + "][" + tag + "]: " + msg + ", by: " + exp);
    }

    public static void logD(String msg) {
        if (logLevel < 4) return;
        XposedBridge.log("[HyperCeiler][D]: " + msg);
    }

    public static void logD(String tag, String pkg, String msg) {
        if (logLevel < 4) return;
        XposedBridge.log("[HyperCeiler][D][" + pkg + "][" + tag + "]: " + msg);
    }

}
