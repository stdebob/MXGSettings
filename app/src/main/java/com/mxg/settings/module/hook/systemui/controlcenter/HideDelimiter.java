// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.controlcenter;

import static com.mxg.settings.utils.PropUtils.getProp;

import android.telephony.SubscriptionInfo;
import android.view.View;
import android.widget.TextView;

import com.mxg.settings.module.base.BaseHook;

import de.robv.android.xposed.XposedHelpers;

public class HideDelimiter extends BaseHook {

    boolean operator = mPrefsMap.getStringAsInt("system_ui_control_center_hide_operator", 0) == 1;
    int prefs = mPrefsMap.getStringAsInt("system_ui_control_center_hide_operator", 0);
    String[] deviceNameList = {getProp("persist.sys.device_name")};

    @Override
    public void init() {
        try {
            findClass("com.android.systemui.statusbar.policy.MiuiCarrierTextController").getDeclaredMethod("fireCarrierTextChanged", String.class);
            findAndHookMethod("com.android.systemui.statusbar.policy.MiuiCarrierTextController",
                    "fireCarrierTextChanged", String.class,
                    new MethodHook() {
                        @Override
                        protected void before(MethodHookParam param) {
                            String mCurrentCarrier = (String) param.args[0];
                            switch (prefs) {
                                case 1 -> param.args[0] = mCurrentCarrier.replace(" | ", "");
                                case 2 -> param.args[0] = "";
                                case 3 -> param.args[0] = getProp("persist.sys.device_name");
                            }
                        }
                    }
            );
        } catch (Throwable e) {
            findAndHookMethod("com.android.systemui.statusbar.policy.MiuiCarrierTextControllerImpl",
                    "addCallback", "com.android.systemui.plugins.miui.statusbar.MiuiCarrierTextController$CarrierTextListener",
                    new MethodHook() {
                        @Override
                        protected void before(MethodHookParam param) {
                            String mCurrentCarrier = (String) XposedHelpers.getObjectField(param.thisObject, "mCurrentCarrier");
                            switch (prefs) {
                                case 1 -> mCurrentCarrier = mCurrentCarrier.replace(" | ", "");
                                case 2 -> mCurrentCarrier = "";
                                case 3 -> mCurrentCarrier = getProp("persist.sys.device_name");
                            }
                            XposedHelpers.setObjectField(param.thisObject, "mCurrentCarrier", mCurrentCarrier);
                        }
                    }
            );

            if (prefs == 3) {
                findAndHookMethod("com.android.keyguard.clock.KeyguardClockContainer$mCarrierTextCallback$1", "onCarrierTextChanged", String.class, new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) throws Throwable {
                        param.args[0] = getProp("persist.sys.device_name");
                    }
                });

                findAndHookMethod("com.android.keyguard.clock.KeyguardClockContainer$mCarrierTextCallback$1", "onCarrierTextChanged", String.class, int.class, new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) throws Throwable {
                        param.args[0] = getProp("persist.sys.device_name");
                        param.args[1] = 1;
                    }
                });

                findAndHookMethod("com.android.keyguard.CarrierText$1", "onCarrierTextChanged", String.class, new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) throws Throwable {
                        param.args[0] = getProp("persist.sys.device_name");
                    }
                });

                findAndHookMethod("com.android.systemui.statusbar.policy.MiuiCarrierTextControllerImpl", "updateCarrierText", new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) throws Throwable {
                        XposedHelpers.setObjectField(param.thisObject, "mCurrentCarrier", getProp("persist.sys.device_name"));
                        XposedHelpers.setObjectField(param.thisObject, "mCustomCarrier", deviceNameList);
                        XposedHelpers.setObjectField(param.thisObject, "mCarrier", deviceNameList);
                        XposedHelpers.setObjectField(param.thisObject, "mRealCarrier", deviceNameList);
                    }
                });

                findAndHookMethod(SubscriptionInfo.class, "getCarrierName", new MethodHook(){
                    @Override
                    protected void before(MethodHookParam param) throws Throwable {
                        param.setResult(getProp("persist.sys.device_name"));
                    }
                });

                findAndHookMethod("com.android.systemui.statusbar.policy.MiuiCarrierTextControllerImpl", "onCarrierChanged", String[].class, new MethodHook() {
                    @Override
                    protected void before(MethodHookParam param) throws Throwable {
                        param.args[0] = deviceNameList;
                        XposedHelpers.setObjectField(param.thisObject, "mRealCarrier", deviceNameList);
                    }
                });

            } else {
                findAndHookMethod("androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0",
                        "m", String.class, String.class, new MethodHook() {
                            @Override
                            protected void before(MethodHookParam param) throws Throwable {
                                super.before(param);
                                // param.args[0] = getProp("persist.sys.device_name");
                                if (param.args[1].equals(" | ")) {
                                    param.args[1] = "";
                                }
                            }
                        }
                );

                findAndHookMethodSilently("androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0",
                        "m", String.class, String.class, String.class,
                        new MethodHook() {
                            @Override
                            protected void before(MethodHookParam param) {
                                // param.args[0] = getProp("persist.sys.device_name");
                                if (param.args[1].equals(" | ")) {
                                    param.args[1] = "";
                                }
                            }
                        }
                );
            }
        }

        if (prefs == 2) {
            MethodHook hideOperatorHook = new MethodHook() {
                @Override
                protected void after(MethodHookParam param) throws Throwable {
                    super.after(param);
                    Object carrierView;
                    TextView mCarrierText;
                    try {
                        carrierView = XposedHelpers.getObjectField(param.thisObject, "carrierText");
                    } catch (Throwable e) {
                        carrierView = XposedHelpers.getObjectField(param.thisObject, "mCarrierText");
                    }
                    mCarrierText = (TextView) carrierView;
                    mCarrierText.setVisibility(View.GONE);
                }
            };

            boolean hookedFlaresInfo = hookAllMethodsBoolean("com.android.systemui.controlcenter.phone.widget.ControlCenterStatusBar",
                    "updateFlaresInfo", hideOperatorHook);
            if (!hookedFlaresInfo) {
                findAndHookMethodSilently("com.android.systemui.controlcenter.phone.widget.ControlCenterStatusBar",
                        "onFinishInflate", hideOperatorHook);
            }

            findAndHookMethodSilently("com.android.systemui.qs.MiuiNotificationHeaderView",
                    "updateCarrierTextVisibility", hideOperatorHook);

            hookedFlaresInfo = findAndHookMethodSilently("com.android.systemui.qs.MiuiQSHeaderView",
                    "updateCarrierVisibility", hideOperatorHook);
            if (!hookedFlaresInfo) {
                findAndHookMethodSilently("com.android.systemui.qs.MiuiQSHeaderView",
                        "onFinishInflate", hideOperatorHook);
            }
        }
    }
}

