package com.blueberrysolution.pinelib21.basic_class_ext

import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

fun String.isDouble(): Boolean{
    val pattern: Pattern = Pattern.compile("^-?\\d*\\.?\\d*\$")
    val isNum: Matcher = pattern.matcher(this)
    return isNum.matches()
}

fun String.toDate(format: String = "yyyy-MM-dd HH:mm:ss"): Date{
    if (this.length == format.length){
        var formatter =  SimpleDateFormat(format);
        var date  = formatter.parse(this);
        return date;
    }
    else{
        var cha = format.length - this.length
        var f1 = format.substring(0, format.length - cha);
        var formatter =  SimpleDateFormat(f1);
        var date  = formatter.parse(this);
        return date;
    }

}