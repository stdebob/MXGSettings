// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.securitycenter.lab

import com.mxg.settings.module.base.*
import com.mxg.settings.module.hook.securitycenter.lab.LabUtilsClass.labUtilClass


object GetNumberEnable : BaseHook() {
    private var labUtils: Class<*>? = null
    override fun init() {
        labUtilClass.forEach {
            labUtils = it
            logI(TAG, this.lpparam.packageName, "labUtils class is $labUtils")
            findAndHookMethod(
                "com.miui.permcenter.settings.PrivacyLabActivity",
                "onCreateFragment",
                object : MethodHook() {
                    @Throws(Throwable::class)
                    override fun before(param: MethodHookParam) {
                        val fm = getStaticObjectFieldSilently(labUtils, "b")
                        if (fm != null) {
                            try {
                                val featMap = fm as MutableMap<String, Int>
                                featMap["mi_lab_operator_get_number_enable"] = 0
                                // featMap.put("mi_lab_blur_location_enable", 0);
                            } catch (ignore: Throwable) {
                            }
                        }
                    }
                })
        }

        /*   val result: List<DexClassDescriptor> = Objects.requireNonNull<List<DexClassDescriptor>>(
               SecurityCenterDexKit.mSecurityCenterResultClassMap.get("LabUtils")
           )
           for (descriptor in result) {
               labUtils = descriptor.getClassInstance(lpparam.classLoader)
               log("labUtils class is $labUtils")
               findAndHookMethod(
                   "com.miui.permcenter.settings.PrivacyLabActivity",
                   "onCreateFragment",
                   object : BaseHook.MethodHook() {
                       @Throws(Throwable::class)
                       protected override fun before(param: MethodHookParam) {
                           val fm = Helpers.getStaticObjectFieldSilently(labUtils, "b")
                           if (fm != null) {
                               try {
                                   val featMap = fm as MutableMap<String, Int>
                                   featMap["mi_lab_operator_get_number_enable"] = 0
                                   // featMap.put("mi_lab_blur_location_enable", 0);
                               } catch (ignore: Throwable) {
                               }
                           }
                       }
                   })
           }
       } catch (e: Throwable) {
           e.printStackTrace()
       }*/
    }
}
