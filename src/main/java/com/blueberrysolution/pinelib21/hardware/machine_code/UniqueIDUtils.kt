package com.blueberrysolution.pinelib21.hardware.machine_code

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.TELEPHONY_SERVICE
import android.os.Build
import android.os.Environment
import android.preference.PreferenceManager
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import com.blueberrysolution.pinelib21.app.c
import java.io.*
import java.util.*


object UniqueIDUtils {
    private const val TAG = "UniqueIDUtils"
    private var uniqueID: String? = null
    private const val uniqueKey = "unique_id"
    private val uniqueIDDirPath: String =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            .getAbsolutePath()
    private const val uniqueIDFile = "unique.txt"
    fun getUniqueID(): String? {
        var context: Context = c()
        //三步读取：内存中，存储的SP表中，外部存储文件中
        if (!TextUtils.isEmpty(uniqueID)) {
            Log.e(TAG, "getUniqueID: 内存中获取$uniqueID")
            return uniqueID
        }
        uniqueID = PreferenceManager.getDefaultSharedPreferences(context)
            .getString(uniqueKey, "")
        if (!TextUtils.isEmpty(uniqueID)) {
            Log.e(TAG, "getUniqueID: SP中获取$uniqueID")
            return uniqueID
        }
        readUniqueFile(context)
        if (!TextUtils.isEmpty(uniqueID)) {
            Log.e(TAG, "getUniqueID: 外部存储中获取$uniqueID")
            return uniqueID
        }
        //两步创建：硬件获取；自行生成与存储
        getDeviceID(context)
        getAndroidID(context)
        sNID
        createUniqueID(context)
        PreferenceManager.getDefaultSharedPreferences(context).edit()
            .putString(uniqueKey, uniqueID)
        return uniqueID
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceID(context: Context) {
        if (!TextUtils.isEmpty(uniqueID)) {
            return
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O_MR1) {
            return
        }
        var deviceId: String? = null
        try {
            deviceId =
                (context.getSystemService(TELEPHONY_SERVICE) as TelephonyManager).deviceId
            //华为MatePad上，神奇的获得unknown，特此修复
            if (TextUtils.isEmpty(deviceId) || "unknown" == deviceId) {
                return
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }
        uniqueID = deviceId
        Log.e(TAG, "getUniqueID: DeviceId获取成功$uniqueID")
    }

    private fun getAndroidID(context: Context) {
        if (!TextUtils.isEmpty(uniqueID)) {
            return
        }
        var androidID: String? = null
        try {
            androidID =
                Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)
            if (TextUtils.isEmpty(androidID) || "9774d56d682e549c" == androidID) {
                return
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }
        uniqueID = androidID
        Log.e(TAG, "getUniqueID: AndroidID获取成功$uniqueID")
    }

    private val sNID: Unit
        private get() {
            if (!TextUtils.isEmpty(uniqueID)) {
                return
            }
            val snID = Build.SERIAL
            if (TextUtils.isEmpty(snID)) {
                return
            }
            uniqueID = snID
            Log.e(TAG, "getUniqueID: SNID获取成功$uniqueID")
        }

    private fun createUniqueID(context: Context) {
        if (!TextUtils.isEmpty(uniqueID)) {
            return
        }
        uniqueID = UUID.randomUUID().toString()
        Log.e(TAG, "getUniqueID: UUID生成成功$uniqueID")
        val filesDir = File(
            uniqueIDDirPath + File.separator + context.getApplicationContext()
                .getPackageName()
        )
        if (!filesDir.exists()) {
            filesDir.mkdir()
        }
        val file = File(filesDir, uniqueIDFile)
        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        var outputStream: FileOutputStream? = null
        try {
            outputStream = FileOutputStream(file)
            outputStream.write(uniqueID!!.toByteArray())
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush()
                    outputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun readUniqueFile(context: Context) {
        val filesDir = File(
            uniqueIDDirPath + File.separator + context.getApplicationContext()
                .getPackageName()
        )
        val file = File(filesDir, uniqueIDFile)
        if (file.exists()) {
            var inputStream: FileInputStream? = null
            try {
                inputStream = FileInputStream(file)
                val bytes = ByteArray(file.length() as Int)
                inputStream.read(bytes)
                uniqueID = String(bytes)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    fun clearUniqueFile(context: Context) {
        val filesDir = File(
            uniqueIDDirPath + File.separator + context.getApplicationContext()
                .getPackageName()
        )
        deleteFile(filesDir)
    }

    private fun deleteFile(file: File) {
        if (file.isDirectory()) {
            for (listFile in file.listFiles()) {
                deleteFile(listFile)
            }
        } else {
            file.delete()
        }
    }
}