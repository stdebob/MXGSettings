// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.utils.blur.zhenxiang

import android.content.*
import android.widget.*

class BlurLinearLayout constructor(context: Context) : LinearLayout(context) {
    val blurController: SystemBlurController = SystemBlurController(this)
}
