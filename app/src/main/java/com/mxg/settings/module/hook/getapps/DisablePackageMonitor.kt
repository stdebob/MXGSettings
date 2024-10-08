// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.getapps

import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.mxg.settings.module.base.*

object DisablePackageMonitor : BaseHook() {

    override fun init() {
        // 使用root, adb, packageinstaller安装应用后, 应用商店有后台时会上传检查应用更新信息
        val initMethod = findClass("com.xiaomi.market.receiver.MyPackageMonitor").getMethod("init")
        initMethod.createHook {
            logD(TAG, lpparam.packageName, "FindAndHook 'init' method: $initMethod")
            replace { }
        }
    }

}
