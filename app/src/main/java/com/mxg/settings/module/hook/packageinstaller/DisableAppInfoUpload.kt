// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.packageinstaller

import android.content.*
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.mxg.settings.*
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.toElementList
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod
import org.luckypray.dexkit.query.matchers.base.*
import java.lang.reflect.*
import java.util.stream.*


object DisableAppInfoUpload : BaseHook() {

    override fun init() {
        disableInvokeInfoLayoutApi()
        disableInvokeInterceptCheckApi()
        disableInvokeAvlUploadApi()
    }

    /**
     * after installing
     */
    private fun disableInvokeAvlUploadApi() {
        /**
         * methods invoke api '/avl/upload/'
         */
        val avlUploadInvokerList = DexKit.getDexKitBridgeList("avlUploadInvokerList") {
            it.findMethod {
                matcher {
                    paramCount(4)
                    paramTypes(
                        null,
                        findClass("com.miui.packageInstaller.model.ApkInfo"),
                        Boolean::class.javaPrimitiveType,
                        null
                    )
                    usingStrings("appSourcepackageName", "packageName")
                    returnType(Void::class.javaPrimitiveType as Class<*>)
                    modifiers(AccessFlagsMatcher.create(Modifier.STATIC))
                }
            }.toElementList()
        }.toMethodList()

        if (avlUploadInvokerList.isEmpty()) {
            throw IllegalStateException("cannot find MethodData invoking '/avl/upload/'")
        }
        logD("/avl/upload/", avlUploadInvokerList)

        avlUploadInvokerList.forEach {
            it.createHook {
                replace { }
            }
        }
    }

    private fun disableInvokeInterceptCheckApi() {
        /**
         * methods invoke api '/v4/game/interceptcheck/'
         */
        val interceptCheckInvokerList = DexKit.getDexKitBridgeList("interceptCheckInvokerList") {
            it.findMethod {
                matcher {
                    paramCount(6)
                    paramTypes(
                        Context::class.java,
                        null,
                        Int::class.javaPrimitiveType,
                        findClass("com.miui.packageInstaller.model.ApkInfo"),
                        HashMap::class.java,
                        null
                    )
                    returnType(Object::class.java)
                    usingStrings("device_type", "packageName", "installationMode", "apk_bit")
                }
            }.toElementList()
        }.toMethodList()

        if (interceptCheckInvokerList.isEmpty()) {
            throw IllegalStateException("cannot find MethodData invoking 'interceptcheck'")
        }
        logD("/interceptcheck", interceptCheckInvokerList)

        interceptCheckInvokerList.forEach {
            it.createHook {
                returnConstant(null)
            }
        }
    }

    private fun disableInvokeInfoLayoutApi() {
        /**
         * methods invoke api '/info/layout'
         */
        val infoLayoutInvokerList = DexKit.getDexKitBridgeList("interceptCheckInvokerList") {
            it.findMethod {
                matcher {
                    paramCount(7)
                    paramTypes(
                        String::class.java,
                        String::class.java,
                        String::class.java,
                        Integer::class.java,
                        String::class.java,
                        String::class.java,
                        null
                    )
                }
            }.toElementList()
        }.toMethodList()

        if (infoLayoutInvokerList.isEmpty()) {
            throw IllegalStateException("cannot find MethodData invoking '/info/layout'")
        }
        logD("/info/layout'", infoLayoutInvokerList)

        infoLayoutInvokerList.forEach { method ->
            method.createHook {
                if (method.returnType == Object::class.java) {
                    returnConstant(null)
                } else {
                    replace { }
                }
            }
        }

        mResHook.setResReplacement("com.miui.packageinstaller", "layout", "layout_network_error", R.layout.replacement_empty)
        mResHook.setResReplacement("com.miui.packageinstaller", "layout", "safe_mode_layout_network_error", R.layout.replacement_empty)
    }

    private fun logD(prefix: String, list:  List<Method>) {
        logD(
            TAG, lpparam.packageName,
            "'${prefix}' find methods: " + list.stream().map {
                "${it.javaClass.name}#${
                    it.toMethod().name
                }"
            }.collect(Collectors.joining(" | "))
        )
    }
}
