package com.blueberrysolution.pinelib21.file

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import com.blueberrysolution.pinelib21.app.c
import com.blueberrysolution.pinelib21.context.a
import java.io.File
/*
* 需要在androidManifest 中的 application 下 加
        <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="包名.provider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_paths" />
        </provider>

*
* */

fun File.install(){
    try { //这里有文件流的读写，需要处理一下异常
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //如果SDK版本>=24，即：Build.VERSION.SDK_INT >= 24
            val packageName: String = c().getPackageName()
            val authority ="com.blueberrysolution.pinelib21.provider";
            var uri = FileProvider.getUriForFile(c(), authority, this)
            intent.setDataAndType(uri, "application/vnd.android.package-archive")
        } else {
            var uri = Uri.fromFile(this)
            intent.setDataAndType(uri, "application/vnd.android.package-archive")
        }
        a().startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}