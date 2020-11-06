package com.blueberrysolution.pinelib21.basic_class_ext

import java.util.regex.Matcher
import java.util.regex.Pattern

fun String.isDouble(): Boolean{
    val pattern: Pattern = Pattern.compile("-?[1-9]*(\\.\\d*)?\$|^-?d^(\\.\\d*)?")
    val isNum: Matcher = pattern.matcher(this)
    return isNum.matches()
}