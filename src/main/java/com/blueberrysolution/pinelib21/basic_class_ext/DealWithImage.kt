package com.blueberrysolution.pinelib21.basic_class_ext

import com.blueberrysolution.pinelib21.app.c

fun dip2px(dipValue: Float): Int {
    val scale: Float = c().getResources().getDisplayMetrics().density
    return (dipValue * scale + 0.5f).toInt()
}

fun px2dip(pxValue: Float): Int {
    val scale: Float = c().getResources().getDisplayMetrics().density
    return (pxValue / scale + 0.5f).toInt()
}