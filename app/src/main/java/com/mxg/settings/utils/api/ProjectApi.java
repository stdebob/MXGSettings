// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.utils.api;

import com.mxg.settings.BuildConfig;

public class ProjectApi {
    public static final String mAppModulePkg = BuildConfig.APPLICATION_ID;

    public static String getBuildType() {
        switch (BuildConfig.BUILD_TYPE) {
            case "release" -> {
                return "release";
            }
            case "beta" -> {
                return "beta";
            }
            case "canary" -> {
                return "canary";
            }
            case "debug" -> {
                return "debug";
            }
            default -> {
                return "unknown";
            }
        }
    }

    public static boolean isRelease() {
        return "release".equals(BuildConfig.BUILD_TYPE);
    }

    public static boolean isBeta() {
        return "beta".equals(BuildConfig.BUILD_TYPE);
    }

    public static boolean isCanary() {
        return "canary".equals(BuildConfig.BUILD_TYPE);
    }

    public static boolean isDebug() {
        return "debug".equals(BuildConfig.BUILD_TYPE);
    }
}
