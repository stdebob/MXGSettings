// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.base.dexkit

import org.luckypray.dexkit.query.enums.*
import org.luckypray.dexkit.query.matchers.*
import org.luckypray.dexkit.result.*
import java.lang.reflect.*

/**
 * DexKit 工具
 */
object DexKitTool {
    /**
     * 将 ClassData 列表快捷转为 List<AnnotatedElement>
     * 使用时在查找后调用 .toElementList(EzXHelper.safeClassLoader) 即可
     */
    fun ClassDataList.toElementList(): List<AnnotatedElement> {
        return DexKit.toElementList(this)
    }

    /**
     * 将 MethodData 列表快捷转为 List<AnnotatedElement>
     * 使用时在查找后调用 .toElementList(EzXHelper.safeClassLoader) 即可
     */
    fun MethodDataList.toElementList(): List<AnnotatedElement> {
        return DexKit.toElementList(this)
    }

    /**
     * 将 FieldData 列表快捷转为 List<AnnotatedElement>
     * 使用时在查找后调用 .toElementList(EzXHelper.safeClassLoader) 即可
     */
    fun FieldDataList.toElementList(): List<AnnotatedElement> {
        return DexKit.toElementList(this)
    }

    fun List<*>.toElementList(): List<AnnotatedElement> {
        return DexKit.toElementList(this)
    }

    fun BaseDataList<*>.toElementList(): List<AnnotatedElement> {
        return DexKit.toElementList(this)
    }

    /**
     * 快捷转为 MethodDataList
     */
    fun BaseDataList<*>.toMethodDataList(): MethodDataList {
        return this as MethodDataList
    }

    /**
     * 快捷转为 FieldDataList
     */
    fun BaseDataList<*>.toFieldDataList(): FieldDataList {
        return this as FieldDataList
    }

    /**
     * 快捷转为 ClassDataList
     */
    fun BaseDataList<*>.toClassDataList(): ClassDataList {
        return this as ClassDataList
    }

    /**
     * 快捷类型转换为 Method
     */
    fun AnnotatedElement.toMethod(): Method {
        return this as Method
    }

    /**
     * 快捷类型转换为 Field
     */
    fun AnnotatedElement.toField(): Field {
        return this as Field
    }

    /**
     * 快捷类型转换为 Class
     */
    fun AnnotatedElement.toClass(): Class<*> {
        return this as Class<*>
    }

    /**
     * 快捷类型转换为 Constructor
     */
    fun AnnotatedElement.toConstructor(): Constructor<*> {
        return this as Constructor<*>
    }

    /**
     * DexKit 封装查找方式
     */
    fun MethodMatcher.addUsingStringsEquals(vararg strings: String) {
        for (string in strings) {
            addUsingString(string, StringMatchType.Equals)
        }
    }

    fun ClassMatcher.addUsingStringsEquals(vararg strings: String) {
        for (string in strings) {
            addUsingString(string, StringMatchType.Equals)
        }
    }
}
