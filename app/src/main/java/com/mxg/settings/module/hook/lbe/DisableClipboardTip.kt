// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.lbe

import android.content.*
import android.content.pm.*
import android.os.*
import android.widget.*
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.mxg.settings.*
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.tool.OtherTool.*
import de.robv.android.xposed.*
import de.robv.android.xposed.callbacks.*


object DisableClipboardTip : BaseHook() {
    private val mDisableClipboardTip by lazy {
        mPrefsMap.getBoolean("lbe_disable_clipboard_tip")
    }

    private val permissionRequestClass by lazy {
        loadClass("com.lbe.security.sdk.PermissionRequest", lpparam.classLoader)
    }

    override fun init() {
        XposedHelpers.findAndHookMethod(
            "com.lbe.security.ui.SecurityPromptHandler",
            lpparam.classLoader,
            "handleNewRequest",
            permissionRequestClass,
            object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    val permissionRequest = param.args[0]
                    val permission: Long =
                        XposedHelpers.callMethod(permissionRequest, "getPermission") as Long

                    // PermissionManager.PERM_ID_READ_CLIPBOARD
                    if (permission == 274877906944L) {
                        val packageName =
                            XposedHelpers.callMethod(permissionRequest, "getPackage") as String
                        val context =
                            XposedHelpers.getObjectField(param.thisObject, "mContext") as Context
                        val appName = getAppName(context, packageName)
                        val modRes = getModuleRes(context)

                        if (!mDisableClipboardTip) {
                            Toast.makeText(context, "$appName ${modRes.getString(R.string.lbe_clipboard_tip)}", Toast.LENGTH_SHORT).show()
                        }
                        hideDialog(lpparam, packageName, param)

                        logI(
                            TAG,
                            this@DisableClipboardTip.lpparam.packageName,
                            " $packageName -> $appName read clipboard."
                        )
                    }
                }
            })
    }

    fun getAppName(context: Context, packageName: String): String {
        val pm: PackageManager = context.applicationContext.packageManager
        val ai: ApplicationInfo =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                pm.getApplicationInfo(packageName, PackageManager.ApplicationInfoFlags.of(0))
            } else {
                pm.getApplicationInfo(packageName, 0)
            }
        return (pm.getApplicationLabel(ai)) as String
    }

    private fun hideDialog(
        lpparam: XC_LoadPackage.LoadPackageParam,
        packageName: String,
        param: XC_MethodHook.MethodHookParam
    ) {
        val clipData = XposedHelpers.findClass(
            "com.lbe.security.utility.AnalyticsHelper",
            lpparam.classLoader
        )
        val hashMap = HashMap<String, String>()
        hashMap["pkgName"] = packageName
        hashMap["count"] = "click"
        XposedHelpers.callStaticMethod(
            clipData,
            "recordCountEvent",
            "clip",
            "ask_allow",
            hashMap
        )

        XposedHelpers.callMethod(param.thisObject, "gotChoice", 3, true, true)
        XposedHelpers.callMethod(param.thisObject, "onStop")
    }
}
