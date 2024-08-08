// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.utils.log;


import static com.mxg.settings.utils.devicesdk.DeviceSDKKt.getSerial;

import android.util.Log;

import com.mxg.settings.BuildConfig;
import com.mxg.settings.module.base.BaseXposedInit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LogManager {
    public static final int logLevel = getLogLevel();

    public static boolean IS_LOGGER_ALIVE;
    public static String LOGGER_CHECKER_ERR_CODE;

    public static int getLogLevel() {
        int level = BaseXposedInit.mPrefsMap.getStringAsInt("log_level", 3);
        return BuildConfig.BUILD_TYPE.equals("canary") ? (level != 3 && level != 4 ? 3 : level) : level;
    }

    public static String logLevelDesc() {
        return switch (logLevel) {
            case 0 -> ("Disable");
            case 1 -> ("Error");
            case 2 -> ("Warn");
            case 3 -> ("Info");
            case 4 -> ("Debug");
            default -> ("Unknown");
        };
    }

    public static boolean isLoggerAlive() {
        String tag = "HyperCeilerLogManager";
        String message = "LOGGER_ALIVE_SYMBOL_" + getSerial();
        int timeout = 5;
        Log.d(tag, message);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Boolean> future = executor.submit(() -> {
            try {
                Process process = Runtime.getRuntime().exec("logcat -d " + tag + ":D *:S");
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains(message)) {
                        LOGGER_CHECKER_ERR_CODE = "SUCCESS";
                        return true;
                    }
                }
            } catch (Exception e) {
                LOGGER_CHECKER_ERR_CODE = String.valueOf(e);
            }
            LOGGER_CHECKER_ERR_CODE = "NO_SUCH_LOG";
            return false;
        });

        try {
            return future.get(timeout, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            LOGGER_CHECKER_ERR_CODE = "TIME_OUT";
            future.cancel(true);
        } catch (Exception e) {
            LOGGER_CHECKER_ERR_CODE = String.valueOf(e);
        } finally {
            executor.shutdownNow();
        }

        LOGGER_CHECKER_ERR_CODE = "WITHOUT_CODE";
        return false;
    }
}
