package com.blueberrysolution.pinelib21.app


import android.content.pm.PackageManager
import com.blueberrysolution.pinelib21.debug.i
import com.blueberrysolution.pinelib21.debug.w
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class ZSignalCheck {
    var thisSignature: String = "";

    init {
        thisSignature = getSignMd5Str();
        if (C.isDebug) {
            if (thisSignature == C.releaseSignature) {
                C.isDebug = false;
                w("Found Release Application, Debug Closed")
            } else {
                i("Debug Signature: " + thisSignature);
            }
        }

    }

    /**
     * MD5加密
     * @param byteStr 需要加密的内容
     * @return 返回 byteStr的md5值
     */
    fun encryptionMD5(byteStr: ByteArray): String {
        var messageDigest: MessageDigest? = null;
        var md5StrBuff: StringBuffer = StringBuffer();
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(byteStr);
            var byteArray: ByteArray = messageDigest.digest();
//            return Base64.encodeToString(byteArray,Base64.NO_WRAP);
            for (i in byteArray.indices) {
                if (Integer.toHexString(0xFF and byteArray[i].toInt()).length == 1) {
                    md5StrBuff.append("0")
                        .append(Integer.toHexString(0xFF and byteArray[i].toInt()));
                } else {
                    md5StrBuff.append(Integer.toHexString(0xFF and byteArray[i].toInt()));
                }
            }


        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace();
        }
        return md5StrBuff.toString();
    }

    /**
     * 获取app签名md5值,与“keytool -list -keystore D:\Desktop\app_key”‘keytool -printcert     *file D:\Desktop\CERT.RSA’获取的md5值一样
     */
    fun getSignMd5Str(): String {
        try {
            val packageInfo = app().getPackageManager().getPackageInfo(
                app().getPackageName(), PackageManager.GET_SIGNATURES
            )
            val signs = packageInfo.signatures
            val sign = signs[0]
            return encryptionMD5(sign.toByteArray())
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return ""
    }

}