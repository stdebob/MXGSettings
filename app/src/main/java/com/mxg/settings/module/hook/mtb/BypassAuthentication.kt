// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.mtb

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*
import com.mxg.settings.utils.*
import com.mxg.settings.utils.devicesdk.*

object BypassAuthentication : BaseHook() {
    override fun init() {
        val mModemTestBoxClass = loadClass("com.xiaomi.mtb.activity.ModemTestBoxMainActivity")

        // 在HyperOS上
        runCatching {
            if (isMoreHyperOSVersion(1f)) {
                loadClass("com.xiaomi.mtb.XiaoMiServerPermissionCheck").methodFinder()
                    .filterByName("getClassErrorString")
                    .single().createHook {
                        after {
                            it.result = null
                        }
                    }
            }
        }

        runCatching {
            loadClass("com.xiaomi.mtb.XiaoMiServerPermissionCheck").methodFinder()
                .filterByName("updatePermissionClass")
                .single().createHook {
                    after {
                        it.result = 0L
                    }
                }
        }

        runCatching {
            loadClass("com.xiaomi.mtb.MtbApp").methodFinder()
                .filterByName("setMiServerPermissionClass")
                .single().createHook {
                    before {
                        it.args[0] = 0
                    }
                }
        }

        runCatching {
            mModemTestBoxClass.methodFinder()
                .filterByName("updateClass")
                .single().createHook {
                    before {
                        it.args[0] = 0
                        it.thisObject.setObjectField("mClassNet", 0)
                    }
                }
        }

        runCatching {
            mModemTestBoxClass.methodFinder()
                .filterByName("initClassProduct")
                .single().createHook {
                    after {
                        it.thisObject.setObjectField("mClassProduct", 0)
                    }
                }
        }
    }

}
