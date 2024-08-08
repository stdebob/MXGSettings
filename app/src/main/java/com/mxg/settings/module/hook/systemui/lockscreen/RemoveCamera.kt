// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui.lockscreen

import android.view.View
import android.widget.LinearLayout
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.ObjectUtils
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.BaseHook
import com.mxg.settings.utils.devicesdk.isAndroidVersion
import com.mxg.settings.utils.devicesdk.isMiuiVersion
import com.mxg.settings.utils.devicesdk.isMoreAndroidVersion
import com.mxg.settings.utils.devicesdk.isMoreHyperOSVersion

object RemoveCamera : BaseHook() {
    private val oldClass by lazy {
        loadClassOrNull("com.android.systemui.statusbar.phone.KeyguardBottomAreaView")
    }
    private val newClass by lazy {
        loadClassOrNull("com.android.keyguard.injector.KeyguardBottomAreaInjector")
    }

    override fun init() {
        // 屏蔽右下角组件显示
        if (isMoreHyperOSVersion(1f) && isMoreAndroidVersion(34)) {
            newClass!!.methodFinder().filter {
                name in setOf(
                    "updateRightAffordanceViewLayoutVisibility",
                    "startButtonLayoutAnimate"
                )
            }.toList().createHooks {
                after {
                    val right =
                        ObjectUtils.getObjectOrNullAs<LinearLayout>(it.thisObject, "mRightAffordanceViewLayout") ?: return@after
                    right.visibility = View.GONE
                }
            }
        } else if (isMiuiVersion(14f) && isAndroidVersion(34)) {
            newClass!!.methodFinder().filterByName("onFinishInflate")
                .single().createHook {
                    after {
                        val right =
                            ObjectUtils.getObjectOrNullAs<LinearLayout>(it.thisObject, "mRightAffordanceViewLayout") ?: return@after
                        right.visibility = View.GONE
                    }
                }
        } else {
            oldClass!!.methodFinder().filterByName("onFinishInflate")
                .single().createHook {
                    after {
                        val right =
                            ObjectUtils.getObjectOrNullAs<LinearLayout>(it.thisObject, "mRightAffordanceViewLayout") ?: return@after
                        right.visibility = View.GONE
                    }
                }
        }

        // 屏蔽滑动撞墙动画
        loadClassOrNull("com.android.keyguard.KeyguardMoveRightController")!!.methodFinder()
            .filterByName("onTouchMove")
            .filterByParamCount(2)
            .single().createHook {
                returnConstant(false)
            }
        loadClassOrNull("com.android.keyguard.KeyguardMoveRightController")!!.methodFinder()
            .filterByName("reset")
            .single().createHook {
                returnConstant(null)
            }
    }
}
