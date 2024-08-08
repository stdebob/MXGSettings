// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui

import android.annotation.*
import android.app.*
import android.content.*
import android.graphics.*
import android.util.*
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.tool.OtherTool.*
import com.mxg.settings.utils.*
import java.util.concurrent.*


class StickyFloatingWindowsForSystemUI : BaseHook() {
    private val fwApps: ConcurrentHashMap<String, Pair<Float, Rect?>> = ConcurrentHashMap()

    private fun updateFwApps(context: Context, pkgName: String?, scale: Float?, rect: Rect?) {
        val intent = Intent(ACTION_PREFIX + "updateFwApps")
        if (pkgName != null) {
            intent.putExtra("package", pkgName)
        }
        if (scale != null) {
            intent.putExtra("scale", scale)
        }
        if (rect != null) {
            intent.putExtra("rect", rect)
        }
        context.sendBroadcast(intent)
    }

    private fun addFwApps(context: Context, pkgName: String) {
        updateFwApps(context, pkgName, null, null)
    }

    private fun removeFwApps(context: Context, pkgName: String) {
        val intent = Intent(ACTION_PREFIX + "removeFwApps")
        intent.putExtra("package", pkgName)
        context.sendBroadcast(intent)
    }


    fun unserializeFwApps(data: String?) {
        if (data.isNullOrEmpty()) return
        fwApps.clear()
        val dataArr = data.split("|")
        for (appData in dataArr) {
            if ("" == appData) continue
            val appDataArr = appData.split(":")
            fwApps[appDataArr[0]] = Pair(
                appDataArr[1].toFloat(),
                if ("-" == appDataArr[2]) null else Rect.unflattenFromString(appDataArr[2])
            )
        }
    }
    private val fwBlackList: List<String> = listOf("com.miui.securitycenter", "com.miui.home")

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun init() {

        findContext(FLAG_ALL).registerReceiver(
            object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    val extra = intent.getStringExtra("fwApps")
                    unserializeFwApps(extra)
                    logI(TAG, lpparam.packageName, "unserializeFwApps: $extra")
                }
            },
            IntentFilter(ACTION_PREFIX + "syncFwApps")
        )

        loadClass(
            "com.android.wm.shell.miuifreeform.MiuiInfinityModeTaskOperations",
        ).methodFinder().filterByName("resizedTask").single().createHook {
            after {
                val taskWrapperInfo = it.args[0]
                val scale = taskWrapperInfo.callMethod("getDestinationNormalScale") as Float
                val rect = taskWrapperInfo.callMethod("getDestinationBounds") as Rect
                val taskInfo = taskWrapperInfo.callMethod("getTaskInfo") as TaskInfo
                val pkgName = taskInfo.baseIntent.component!!.packageName
                val context = it.thisObject.getObjectField("mContext") as Context
                logI(TAG, lpparam.packageName, "resizedTask: $pkgName, $scale, $rect")
                if (fwApps.containsKey(pkgName)) {
                    updateFwApps(context, pkgName, scale, rect)
                }
            }
        }
        loadClass(
            "com.android.wm.shell.miuifreeform.MiuiInfinityModeTaskOperations",
        ).methodFinder().filterByName("setFreeformDestBoundsAndScale").single().createHook {
            after {
                val pkgName = it.args[0].getObjectField("mPackageName").toString()
                val rect = it.args[1] as Rect
                val scale = it.args[2] as Float
                val context = it.thisObject.getObjectField("mContext") as Context
                logI(
                    TAG,
                    lpparam.packageName,
                    "setFreeformDestBoundsAndScale: $pkgName, $scale, $rect"
                )
                updateFwApps(context, pkgName, scale, rect)
            }
        }
        loadClass(
            "com.android.wm.shell.miuifreeform.MiuiFreeformModeTaskInfo",
        ).methodFinder().filterByName("setBounds").single().createHook {
            after {
                val rect = it.args[0] as Rect? ?: return@after
                val mMode = it.thisObject.getObjectField("mMode") as Int
                if (mMode != 0) return@after
                val taskInfo =
                    it.thisObject.callMethod("getState")!!.getObjectField("mTaskInfo") as TaskInfo
                val pkgName = taskInfo.baseIntent.component!!.packageName
                val context = it.thisObject.getObjectField("mContext") as Context
                logI(TAG, lpparam.packageName, "setBounds: $pkgName, $rect")
                updateFwApps(context, pkgName, null, rect)
            }
        }
        loadClass(
            "com.android.wm.shell.miuifreeform.MiuiFreeformModeTaskInfo",
        ).methodFinder().filterByName("setScale").single().createHook {
            after {
                val scale = it.args[0] as Float
                if (scale.isNaN()) return@after
                val mMode = it.thisObject.getObjectField("mMode") as Int
                if (mMode != 0) return@after
                if (!(it.thisObject.callMethod("isExiting") as Boolean)) return@after
                val taskInfo =
                    it.thisObject.callMethod("getState")!!.getObjectField("mTaskInfo") as TaskInfo
                val pkgName = taskInfo.baseIntent.component!!.packageName
                val context = it.thisObject.getObjectField("mContext") as Context
                logI(TAG, lpparam.packageName, "setScale: $pkgName, $scale")

                updateFwApps(context, pkgName, scale, null)
            }
        }
        loadClass(
            "com.android.wm.shell.miuimultiwinswitch.miuiwindowdecor.MiuiBaseWindowDecoration",
        ).methodFinder().filterByName("updateViews").single().createHook {
            before {
                val taskInfo =
                    it.thisObject.getObjectField("mTaskInfo") as ActivityManager.RunningTaskInfo

                val pkgName = taskInfo.baseActivity?.packageName
                if (pkgName.isNullOrBlank()) return@before
                if (fwBlackList.contains(pkgName)) return@before
                if (!taskInfo.getBooleanField("isFocused")) return@before
                val windowingMode = taskInfo.callMethod("getWindowingMode") as Int
                val context = it.thisObject.getObjectField("mContext") as Context
                if (windowingMode == 5) {
                    // 小窗
                    if (!fwApps.containsKey(pkgName)) {
                        logI(TAG, lpparam.packageName, "shouldHideCaption: add $pkgName")
                        addFwApps(context, pkgName)
                    }
                } else if (windowingMode == 1) {
                    // 全屏
                    if (fwApps.containsKey(pkgName)) {
                        logI(TAG, lpparam.packageName, "shouldHideCaption: remove $pkgName")
                        removeFwApps(context, pkgName)
                    }
                }
            }
        }
    }
}
