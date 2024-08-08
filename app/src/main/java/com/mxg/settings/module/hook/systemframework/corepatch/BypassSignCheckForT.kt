// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemframework.corepatch

import com.mxg.settings.module.base.BaseHook


object BypassSignCheckForT : BaseHook() {
    override fun init() {
        try {
            hookAllMethods("android.util.apk.ApkSignatureVerifier", "getMinimumSignatureSchemeVersionForTargetSdk", object : MethodHook() {
                override fun after(param: MethodHookParam?) {
                    param?.result = 1
                }
            })
        } catch (e: Throwable) {
            logE(TAG, this.lpparam.packageName, e)
        }
    }
}
