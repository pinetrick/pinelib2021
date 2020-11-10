package com.blueberrysolution.pinelib21.basic_class_ext

import java.text.DecimalFormat
import java.text.NumberFormat

fun Double.toMoney(): String{
    val nf: NumberFormat = DecimalFormat("#,##0.00")
    return nf.format(this)
}