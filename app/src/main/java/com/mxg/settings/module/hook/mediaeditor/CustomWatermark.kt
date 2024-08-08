// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.mediaeditor

import com.github.kyuubiran.ezxhelper.*
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.mxg.settings.module.base.*
import com.mxg.settings.module.base.dexkit.*
import com.mxg.settings.module.base.dexkit.DexKitTool.toMethod
import org.luckypray.dexkit.query.enums.*

object CustomWatermark : BaseHook() {
    private val name by lazy {
        mPrefsMap.getString("mediaeditor_custom_watermark", "")
    }

    // by StarVoyager
    private val search by lazy {
        DexKit.getDexKitBridge("CustomWatermark") {
            it.findMethod {
                matcher {
                    addUsingString("K30 Pro Zoom E", StringMatchType.Equals)
                    // modifiers = Modifier.FINAL // 1.6.5.10.2 改成了 STATIC，所以寄了
                    returnType = "java.lang.String"
                    paramCount = 2
                }
            }.single().getMethodInstance(EzXHelper.classLoader)
        }.toMethod()
    }

    override fun init() {
        logD(TAG, lpparam.packageName, "[CustomWatermark] search method is $search")
        search.createHook {
            // 当前只能修改后缀
            returnConstant(name)
        }

        /*SystemProperties.methodFinder()
            .filterByParamCount(2)
            .filterByParamTypes(String::class.java, String::class.java)
            .toList().createHooks {
                before {
                    if (it.args[0] == "ro.product.marketname") {
                        it.args[1] = name
                    }
                }
            }

        SystemProperties.methodFinder()
            .filterByName("get")
            .filterByParamTypes(String::class.java)
            .toList().createHooks {
                before {
                    if (it.args[0] == "ro.product.marketname") {
                        it.result = name
                    }
                }
            }*/
    }
}
