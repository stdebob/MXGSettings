// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.aiasst;

import android.content.Context;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class AiCaptions extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        Class<?> mSupportAiSubtitlesUtils = findClassIfExists("com.xiaomi.aiasst.vision.utils.SupportAiSubtitlesUtils");
        Class<?> mSystemUtils = findClassIfExists("com.xiaomi.aiasst.vision.utils.SystemUtils");
        Class<?> mWhitelistChecker = findClassIfExists("com.xiaomi.aiasst.vision.picksound.whitelist.WhitelistChecker");
        Class<?> mMMKVUtils = findClassIfExists("com.xiaomi.aiasst.vision.utils.MMKVUtils");

        try {
            XposedHelpers.setStaticBooleanField(mWhitelistChecker, "mVerified", true);
        } catch (Throwable ignored) {
        }

        try {
            findAndHookMethod(mSupportAiSubtitlesUtils, "isSupportAiSubtitles", Context.class, new MethodHook() {
                @Override
                protected void before(MethodHookParam param) throws Throwable {
                    param.setResult(true);
                }
            });
        } catch (Throwable ignored) {
        }
        try {
            findAndHookMethod(mSupportAiSubtitlesUtils, "isSupportOfflineAiSubtitles", Context.class, new MethodHook() {
                @Override
                protected void before(MethodHookParam param) throws Throwable {
                    param.setResult(true);
                }
            });
        } catch (Throwable ignored) {
        }
        try {
            findAndHookMethod(mSupportAiSubtitlesUtils, "deviceWhetherSupportOfflineSubtitles", Context.class, new MethodHook() {
                @Override
                protected void before(MethodHookParam param) throws Throwable {
                    param.setResult(true);
                }
            });
        } catch (Throwable ignored) {
        }
        try {
            findAndHookMethod(mSupportAiSubtitlesUtils, "isSupportJapanKorea", Context.class, new MethodHook() {
                @Override
                protected void before(MethodHookParam param) throws Throwable {
                    param.setResult(true);
                }
            });
        } catch (Throwable ignored) {
        }
        try {
            findAndHookMethod(mSupportAiSubtitlesUtils, "isSupportJapanKoreaTranslation", Context.class, new MethodHook() {
                @Override
                protected void before(MethodHookParam param) throws Throwable {
                    param.setResult(true);
                }
            });
        } catch (Throwable ignored) {
        }

        try {

            findAndHookMethod(mMMKVUtils, "isSupportJapanKoreaTranslation", new MethodHook() {
                @Override
                protected void before(MethodHookParam param) throws Throwable {
                    param.setResult(true);
                }
            });
        } catch (Throwable ignored) {
        }

        try {

            findAndHookMethod(mMMKVUtils, "isSupportNewSimultaneousInterpretation", new MethodHook() {
                @Override
                protected void before(MethodHookParam param) throws Throwable {
                    param.setResult(true);
                }
            });
        } catch (Throwable ignored) {
        }
        try {
            findAndHookMethod(mSystemUtils, "isSupportAiPickSoundDevice", new MethodHook() {
                @Override
                protected void before(MethodHookParam param) throws Throwable {
                    param.setResult(true);
                }
            });
        } catch (Throwable ignored) {
        }
    }
}
