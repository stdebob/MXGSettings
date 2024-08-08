// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.utils.api.miuiStringToast.res

class Right {
    private var iconParams: IconParams? = null
    private var textParams: TextParams? = null

    fun setIconParams(iconParams: IconParams?) {
        this.iconParams = iconParams
    }

    fun setTextParams(textParams: TextParams?) {
        this.textParams = textParams
    }

    fun getIconParams(): IconParams {
        return iconParams!!
    }

    fun getTextParams(): TextParams {
        return textParams!!
    }

    override fun toString(): String {
        return "Right{iconParams=$iconParams, textParams=$textParams}"
    }
}
