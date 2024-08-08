// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.utils.devicesdk

import android.annotation.*
import android.content.res.*
import android.graphics.*
import com.github.kyuubiran.ezxhelper.*
import com.mxg.settings.utils.PropUtils.*
import com.mxg.settings.utils.shell.ShellUtils.*
import moralnorm.internal.utils.*
import java.security.*
import java.util.*


fun getFingerPrint(): String = android.os.Build.FINGERPRINT
fun getLocale(): String = getProp("ro.product.locale")
fun getLanguage(): String = Locale.getDefault().toString()
fun getBoard(): String = android.os.Build.BOARD
fun getSoc(): String = getProp("ro.soc.model")
fun getDeviceName(): String = android.os.Build.DEVICE
fun getMarketName(): String = getProp("ro.product.marketname")
fun getModelName(): String = android.os.Build.MODEL
fun getBrand(): String = android.os.Build.BRAND
fun getManufacture(): String = android.os.Build.MANUFACTURER
fun getModDevice(): String = getProp("ro.product.mod_device")
fun getCharacteristics(): String = getProp("ro.build.characteristics")
fun getSerial(): String = safeExecCommandWithRoot("getprop ro.serialno").replace("\n", "")
fun getCpuId(): String = removeLeadingZeros(safeExecCommandWithRoot("getprop ro.boot.cpuid"))

fun getDensityDpi(): Int =
    (EzXHelper.appContext.resources.displayMetrics.widthPixels / EzXHelper.appContext.resources.displayMetrics.density).toInt()

@SuppressLint("DiscouragedApi")
fun getCornerRadiusTop(): Int {
    val resourceId = EzXHelper.appContext.resources.getIdentifier(
        "rounded_corner_radius_top", "dimen", "android"
    )
    return if (resourceId > 0) {
        EzXHelper.appContext.resources.getDimensionPixelSize(resourceId)
    } else 100
}

fun isTablet(): Boolean = Resources.getSystem().configuration.smallestScreenWidthDp >= 600
fun isPadDevice(): Boolean = isTablet() || DeviceHelper.isFoldDevice()
fun isDarkMode(): Boolean =
    EzXHelper.appContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
fun colorFilter(colorInt: Int) = BlendModeColorFilter(colorInt, BlendMode.SRC_IN)

fun getDeviceToken(androidId : String): String {
    val modelName = getModelName()
    val cpuId = getCpuId()
    val serial = getSerial()

    val originalText = "[$modelName]&&[$serial]&&[$androidId]&&[$cpuId]"

    return hashString(originalText, "SHA-256")
}

fun hashString(input: String, algorithm: String): String {
    val bytes = input.toByteArray()
    val digest = MessageDigest.getInstance(algorithm)
    val hashBytes = digest.digest(bytes)
    return hashBytes.joinToString("") { "%02x".format(it) }
}

fun removeLeadingZeros(input: String): String {
    var result = input
    while (result.startsWith("0") || result.startsWith("x")) {
        result = result.drop(1)
    }
    return result
}
