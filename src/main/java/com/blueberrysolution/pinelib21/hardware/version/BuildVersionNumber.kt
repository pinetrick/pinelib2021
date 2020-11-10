package com.blueberrysolution.pinelib21.hardware.version

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import com.blueberrysolution.pinelib21.app.PineApplication
import com.blueberrysolution.pinelib21.app.c
import com.blueberrysolution.pinelib21.debug.e


fun PineApplication.getBuildVersionCode_int(): Int{
    var appVersionCode: Long = 0
    try {
        val packageInfo: PackageInfo = c().getApplicationContext()
            .getPackageManager()
            .getPackageInfo(c().getPackageName(), 0)
        appVersionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.longVersionCode
        } else {
            packageInfo.versionCode.toLong()
        }
    } catch (ee: PackageManager.NameNotFoundException) {
        e(ee.message!!)
    }
    return appVersionCode.toInt()
}

fun PineApplication.getBuildVersionName_string(): String{
    var appVersionName = ""
    try {
        val packageInfo: PackageInfo = c().getApplicationContext()
            .getPackageManager()
            .getPackageInfo(c().getPackageName(), 0)
        appVersionName = packageInfo.versionName
    } catch (ee: PackageManager.NameNotFoundException) {
        e(ee.message!!)
    }
    return appVersionName
}