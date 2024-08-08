// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.dock

import android.content.Context
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.hookAfterMethod
import com.mxg.settings.utils.hookBeforeMethod
import de.robv.android.xposed.XC_MethodHook

object FoldDeviceDock : BaseHook() {
    private val mHotSeatsClass by lazy {
        loadClass("com.miui.home.launcher.hotseats.HotSeats")
    }

    override fun init() {
        var hook1: XC_MethodHook.Unhook? = null
        var hook2: XC_MethodHook.Unhook? = null
        var hook3: XC_MethodHook.Unhook? = null

        mHotSeatsClass.methodFinder()
            .filterByName("initContent")
            .single().createHook {
                before {
                    hook1 = "com.miui.home.launcher.DeviceConfig".hookBeforeMethod(
                        "isFoldDevice"
                    ) { hookParam ->
                        hookParam.result = true
                    }
                }
                after {
                    hook1?.unhook()
                }
            }

        try {
            mHotSeatsClass.methodFinder()
                .filterByName("updateContent")
                .single()
        } catch (e: Exception) {
            mHotSeatsClass.methodFinder()
                .filterByName("updateContentView")
                .single()
        }.createHook {
            before {
                hook2 = "com.miui.home.launcher.Application".hookBeforeMethod(
                    "isInFoldLargeScreen"
                ) { hookParam ->
                    hookParam.result = true
                }
            }
            after {
                hook2?.unhook()
            }
        }

        mHotSeatsClass.methodFinder()
            .filterByName("isNeedUpdateItemInfo")
            .single().createHook {
                before {
                    hook3 = "com.miui.home.launcher.Application".hookBeforeMethod(
                        "isInFoldLargeScreen"
                    ) { hookParam -> hookParam.result = true }
                }
                after {
                    hook3?.unhook()
                }
            }

        "com.miui.home.launcher.DeviceConfig".hookAfterMethod(
            "getHotseatMaxCount"
        ) {
            it.result = mPrefsMap.getInt("home_fold_dock_hotseat", 3)
        }

        "com.miui.home.launcher.hotseats.HotSeatsListRecentsAppProvider".hookBeforeMethod(
            "getLimitCount"
        ) {
            it.result = mPrefsMap.getInt("home_fold_dock_run", 2)
        }

        "com.miui.home.launcher.allapps.LauncherMode".hookBeforeMethod(
            "isHomeSupportSearchBar",
            Context::class.java
        ) {
            it.result = false
        }
    }
}
