// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.systemui

import android.content.*
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectUtils.getObjectOrNullAs
import com.github.kyuubiran.ezxhelper.ObjectUtils.invokeMethodBestMatch
import com.github.kyuubiran.ezxhelper.ObjectUtils.setObject
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.mxg.settings.module.base.*

object UnlockClipboard : BaseHook() {
    override fun init() {
        // hook 点来自 淡い夏
        // 解锁原生剪切板编辑框
        // 新方法来自 WOMMO
        val clazzClipboardListener =
            loadClass("com.android.systemui.clipboardoverlay.ClipboardListener")
        if (clazzClipboardListener.declaredFields.any {
                it.name == "sCtsTestPkgList"
            }) clazzClipboardListener.methodFinder().filterByName("onPrimaryClipChanged")
            .filterNonAbstract().single().createHook {
                before {
                    val mClipboardManager =
                        getObjectOrNullAs<ClipboardManager>(it.thisObject, "mClipboardManager")!!
                    val primaryClipSource =
                        invokeMethodBestMatch(mClipboardManager, "getPrimaryClipSource") as String
                    val oldList =
                        getObjectOrNullAs<List<String>>(it.thisObject, "sCtsTestPkgList")!!
                    val newList = mutableListOf<String>().apply {
                        addAll(oldList)
                        if (!contains(primaryClipSource)) add(primaryClipSource)
                    }
                    setObject(it.thisObject, "sCtsTestPkgList", newList)
                }
            }
        else clazzClipboardListener.methodFinder().filterByName("start").filterNonAbstract()
            .single().createHook {
                before {
                    val mClipboardManager =
                        getObjectOrNullAs<ClipboardManager>(it.thisObject, "mClipboardManager")!!
                    mClipboardManager.addPrimaryClipChangedListener(it.thisObject as ClipboardManager.OnPrimaryClipChangedListener?)
                    it.result = null
                }
            }
    }
}
