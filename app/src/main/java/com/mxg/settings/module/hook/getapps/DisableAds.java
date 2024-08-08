// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_

package com.mxg.settings.module.hook.getapps;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class DisableAds extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        Class<?> appDetailV3Cls = findClassIfExists("com.xiaomi.market.common.network.retrofit.response.bean.AppDetailV3");
        Class<?> detailSplashAdManagerCls = findClassIfExists("com.xiaomi.market.ui.splash.DetailSplashAdManager");
        Class<?> splashManagerCls = findClassIfExists("com.xiaomi.market.ui.splash.SplashManager");

        String[] appDetailMethodsTrue = {
                "isBrowserMarketAdOff",
                "isBrowserSourceFileAdOff",
                "supportShowCompat64bitAlert"
        };

        String[] appDetailMethodsFalse = {
                "isInternalAd",
                "needShowAds",
                "needShowAdsWithSourceFile",
                "showComment",
                "showRecommend",
                "showTopBanner",
                "showTopVideo",
                "equals",
                "getShowOpenScreenAd",
                "hasGoldLabel",
                "isBottomButtonLayoutType",
                "isPersonalization",
                "isTopButtonLayoutType",
                "isTopSingleTabMultiButtonType",
                "needShowGrayBtn",
                "needShowPISafeModeStyle",
                "supportAutoLoadDeepLink",
                "supportShowCompatAlert",
                "supportShowCompatChildForbidDownloadAlert"
        };

        String[] splashMethodsFalse = {
                "canShowSplash",
                "needShowSplash",
                "needRequestFocusVideo",
                "isPassiveSplashAd"
        };

        for (String method : appDetailMethodsTrue) {
            hookAllMethods(appDetailV3Cls, method, new MethodHook() {
                @Override
                protected void after(MethodHookParam param) throws Throwable {
                    param.setResult(true);
                }
            });
        }

        for (String method : appDetailMethodsFalse) {
            hookAllMethods(appDetailV3Cls, method, new MethodHook() {
                @Override
                protected void after(MethodHookParam param) throws Throwable {
                    param.setResult(false);
                }
            });
        }

        for (String method : new String[]{
                "canRequestSplashAd",
                "isRequesting",
                "isOpenFromMsa"
        }) {
            hookAllMethods(detailSplashAdManagerCls, method, new MethodHook() {
                @Override
                protected void after(MethodHookParam param) throws Throwable {
                    param.setResult(false);
                }
            });
        }

        hookAllMethods(detailSplashAdManagerCls, "tryToRequestSplashAd", new MethodHook() {
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult(null);
            }
        });

        for (String method : splashMethodsFalse) {
            hookAllMethods(splashManagerCls, method, new MethodHook() {
                @Override
                protected void after(MethodHookParam param) throws Throwable {
                    param.setResult(false);
                }
            });
        }
    }
}
