// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.utils.devicesdk

import android.os.*
import com.mxg.settings.utils.PropUtils.*
import com.mxg.settings.utils.shell.ShellUtils.*


// 设备信息相关
fun getSystemVersionIncremental(): String = if (getProp("ro.mi.os.version.incremental") != null) getProp("ro.mi.os.version.incremental") else getProp("ro.system.build.version.incremental")
fun getBuildDate(): String = getProp("ro.system.build.date")
fun getHost(): String = Build.HOST
fun getBuilder(): String = getProp("ro.build.user")
fun getBaseOs(): String = getProp("ro.build.version.base_os")
fun getRomAuthor(): String = getProp("ro.rom.author") + getProp("ro.romid")
fun getWhoAmI(): String = safeExecCommandWithRoot("whoami")

/**
 * 获取设备 Android 版本
 * @return 一个 Int 值
 */
fun getAndroidVersion(): Int = Build.VERSION.SDK_INT

/**
 * 获取小米设备 MIUI 版本
 * 将获取到的字符串转换为浮点，以提供判断
 * @return 一个 Float 值
 */
fun getMiuiVersion(): Float = if (getProp("ro.miui.ui.version.name") == "V125") 12.5f else try { getProp("ro.miui.ui.version.code").toFloat() } catch (_: Exception) { -1f }

/**
 * 获取小米设备 HyperOS 版本
 * 将获取到的字符串转换为浮点，以提供判断
 * @return 一个 Float 值
 */
fun getHyperOSVersion(): Float = if (getProp("ro.mi.os.version.code") != null) try { getProp("ro.mi.os.version.code").toFloat() } catch (_: Exception) { -1f }else 0f

/**
 * 判断是否为指定某个 Android 版本
 * @param code 传入的 Android SDK Int 数值
 * @return 一个 Boolean 值
 */
fun isAndroidVersion(code: Int): Boolean = getAndroidVersion() == code

/**
 * 判断是否大于某个 Android 版本
 * @param code 传入的 Android SDK Int 数值
 * @return 一个 Boolean 值
 */
fun isMoreAndroidVersion(code: Int): Boolean = getAndroidVersion() >= code

/**
 * 判断是否为指定某个 MIUI 版本
 * @param code 传入的 MIUI 版本 Float 数值
 * @return 一个 Boolean 值
 */
fun isMiuiVersion(code: Float): Boolean = getMiuiVersion() == code

/**
 * 判断是否大于某个 MIUI 版本
 * @param code 传入的 MIUI 版本 Float 数值
 * @return 一个 Boolean 值
 */
fun isMoreMiuiVersion(code: Float): Boolean = getMiuiVersion() >= code

/**
 * 判断是否为指定某个 HyperOS 版本
 * @param code 传入的 HyperOS 版本 Float 数值
 * @return 一个 Boolean 值
 */
fun isHyperOSVersion(code: Float): Boolean = getHyperOSVersion() == code


/**
 * 判断是否大于某个 HyperOS 版本
 * @param code 传入的 HyperOS 版本 Float 数值
 * @return 一个 Boolean 值
 */
fun isMoreHyperOSVersion(code: Float): Boolean = getHyperOSVersion() >= code

