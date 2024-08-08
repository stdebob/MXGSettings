// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter

import android.annotation.SuppressLint
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook

class RemoveOpenAppConfirmationPopup : BaseHook() {
    @SuppressLint("DiscouragedApi")
    override fun init() {
        loadClass("android.widget.TextView").methodFinder()
            .filterByName("setText")
            .filterByParamTypes {
                it[0] == CharSequence::class.java
            }.first().createHook {
                after {
                    val textView = it.thisObject as TextView
                    if (it.args.isNotEmpty() && it.args[0]?.toString().equals(
                            textView.context.resources.getString(
                                textView.context.resources.getIdentifier(
                                    "button_text_accept",
                                    "string",
                                    textView.context.packageName
                                )
                            )
                        )
                    ) {
                        textView.performClick()
                    }
                }
            }
    }
}
