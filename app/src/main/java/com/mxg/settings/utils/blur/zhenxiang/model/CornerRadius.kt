// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.utils.blur.zhenxiang.model

data class CornersRadius(
    val topLeft: Float,
    val topRight: Float,
    val bottomLeft: Float,
    val bottomRight: Float,
) {

    companion object {
        fun all(radius: Float): CornersRadius {
            return CornersRadius(radius, radius, radius, radius)
        }

        fun custom(
            topLeft: Float, topRight: Float, bottomLeft: Float, bottomRight: Float
        ): CornersRadius {
            return CornersRadius(topLeft, topRight, bottomLeft, bottomRight)
        }
    }
}
