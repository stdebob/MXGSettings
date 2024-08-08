// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.controlcenter

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectUtils.getObjectOrNullAs
import com.github.kyuubiran.ezxhelper.ObjectUtils.invokeMethodBestMatch
import com.github.kyuubiran.ezxhelper.ObjectUtils.setObject
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*

object NotificationImportanceHyperOSFix : BaseHook() {
    override fun init() {
        loadClass("com.android.systemui.statusbar.phone.NotificationIconAreaController")
            .methodFinder().filterByName("updateStatusBarIcons")
            .first().createHook {
                before { param ->
                    val mNotificationEntries = getObjectOrNullAs<List<Any>>(
                        param.thisObject,
                        "mNotificationEntries"
                    )!!
                    if (mNotificationEntries.isNotEmpty()) {
                        val list = ArrayList<Any>()
                        mNotificationEntries.forEach {
                            val representativeEntry = invokeMethodBestMatch(
                                it,
                                "getRepresentativeEntry"
                            )!!
                            val importance = invokeMethodBestMatch(
                                representativeEntry,
                                "getImportance"
                            ) as Int
                            if (importance > 1) list.add(it)
                        }
                        if (list.size != mNotificationEntries.size) {
                            setObject(param.thisObject, "mNotificationEntries", list)
                        }
                    }
                }
            }
    }
}
