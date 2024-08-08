// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.other

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.callMethod

object ShortcutItemCount : BaseHook() {
    private val mAppShortcutMenuClass by lazy {
        loadClass("com.miui.home.launcher.shortcuts.AppShortcutMenu")
    }

    override fun init() {
        mAppShortcutMenuClass.methodFinder()
            .filterByName("getMaxCountInCurrentOrientation")
            .single().createHook {
                after {
                    it.result = 20
                }
            }

        mAppShortcutMenuClass.methodFinder()
            .filterByName("getMaxShortcutItemCount")
            .single().createHook {
                after {
                    it.result = 20
                }
            }

        mAppShortcutMenuClass.methodFinder()
            .filterByName("getMaxVisualHeight")
            .single().createHook {
                after {
                    it.result = it.thisObject.callMethod("getItemHeight")
                }
            }

    }
}
