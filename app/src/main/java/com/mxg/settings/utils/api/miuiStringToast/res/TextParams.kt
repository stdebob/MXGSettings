// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.utils.api.miuiStringToast.res

class TextParams {
    private var text: String? = null
    private var textColor = 0

    fun setText(text: String?) {
        this.text = text
    }

    fun setTextColor(textColor: Int) {
        this.textColor = textColor
    }

    fun getText(): String {
        return text!!
    }

    fun getTextColor(): Int {
        return textColor
    }

    override fun toString(): String {
        return "TextParams{text='$text', textColor=$textColor}"
    }
}
