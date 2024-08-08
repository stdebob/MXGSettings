// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_

package com.mxg.settings.module.hook.mms;

import static de.robv.android.xposed.XposedHelpers.getObjectField;
import static de.robv.android.xposed.XposedHelpers.setObjectField;

import com.mxg.settings.module.base.BaseHook;

public class DisableRiskTip extends BaseHook {
    @Override
    public void init() throws NoSuchMethodException {
        findAndHookMethod("com.miui.smsextra.sdk.SmartContact", "isRiskyNumber", new MethodHook(){
            @Override
            protected void before(MethodHookParam param) throws Throwable {
                param.setResult(false);
            }
        });
        findAndHookMethod("g3.a", "c", "com.miui.smsextra.sdk.SmartContact", "com.miui.smsextra.sdk.SmartContact", new MethodHook(){
            @Override
            protected void after(MethodHookParam param) throws Throwable {
                // logD("smsrisk g3.a "+getObjectField(param.args[0], "mRiskType"));
                // 不知道为什么set两遍才能跑，先留在这里吧
                if (getObjectField(param.args[0], "mRiskType") == "11" && mPrefsMap.getBoolean("mms_disable_overseas_risk_tip")) setObjectField(param.args[0], "mRiskType", ""); setObjectField(param.args[0], "mRiskType", "");
                if (getObjectField(param.args[0], "mRiskType") == "12" && mPrefsMap.getBoolean("mms_disable_fraud_risk_tip")) setObjectField(param.args[0], "mRiskType", ""); setObjectField(param.args[0], "mRiskType", "");
                // logD("smsrisk 2 g3.a "+getObjectField(param.args[0], "mRiskType"));
            }
        });
        findAndHookMethod("n6.p", "a", "com.miui.smsextra.sdk.SmartContact", "n6.o", new MethodHook(){
            @Override
            protected void after(MethodHookParam param) throws Throwable {
                // logD("smsrisk n6.p "+getObjectField(param.args[0], "mRiskType"));
                // 不知道为什么set两遍才能跑，先留在这里吧
                if (getObjectField(param.args[0], "mRiskType") == "11" && mPrefsMap.getBoolean("mms_disable_overseas_risk_tip")) setObjectField(param.args[0], "mRiskType", ""); setObjectField(param.args[0], "mRiskType", "");
                if (getObjectField(param.args[0], "mRiskType") == "12" && mPrefsMap.getBoolean("mms_disable_fraud_risk_tip")) setObjectField(param.args[0], "mRiskType", ""); setObjectField(param.args[0], "mRiskType", "");
                // logD("smsrisk 2 n6.p "+getObjectField(param.args[0], "mRiskType"));
            }
        });
    }
}
