package com.blueberrysolution.pinelib21.view.app_update

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.widget.TextView
import com.blueberrysolution.pinelib21.R
import com.blueberrysolution.pinelib21.app.c
import com.blueberrysolution.pinelib21.context.PineActivity
import com.blueberrysolution.pinelib21.debug.i
import com.blueberrysolution.pinelib21.file.install
import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


class AppUpdateActivity :PineActivity(){

    lateinit var version_numer: TextView;
    lateinit var features: TextView;
    lateinit var update_btn: TextView;

    var isDownloading = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_update_activity)

        version_numer = findViewById(R.id.version_numer)
        features = findViewById(R.id.features)
        update_btn = findViewById(R.id.update_btn)

        var vn = "v" + AppUpdate.version;
        if (AppUpdate.file_size_mb > 0){
            vn +=  " (" + AppUpdate.file_size_mb + " MB)";
        }

        version_numer.setText(vn)
        features.setText(AppUpdate.note)
        update_btn.setOnClickListener {
            if (!isDownloading){
                isDownloading = true;
                downloadFile();

            }


        }

    }


    fun downloadFile() {
        val url = AppUpdate.downloadLink
        val startTime = System.currentTimeMillis()
        i("startTime=$startTime")
        val okHttpClient = OkHttpClient()
        val request: Request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).enqueue (object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // 下载失败
                e.printStackTrace()
                i("download failed")
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                var `is`: InputStream? = null
                val buf = ByteArray(2048)
                var len = 0
                var fos: FileOutputStream? = null
                // 储存下载文件的目录
                val savePath: String = c().filesDir.absolutePath + "/apk"
                try {

                    `is` = response.body!!.byteStream()
                    val total: Long = response.body!!.contentLength()
                    val file = File(savePath, url.substring(url.lastIndexOf("/") + 1))
                    if (!file.getParentFile().exists()) {
                        //父目录不存在 创建父目录
                        if (!file.getParentFile().mkdirs()) {
                            return;
                        }
                    }
                    fos = FileOutputStream(file)
                    var sum: Long = 0
                    while (`is`.read(buf).also({ len = it }) != -1) {
                        fos.write(buf, 0, len)
                        sum += len.toLong()
                        val progress = (sum * 1.0f / total * 100).toInt()
                        // 下载中
                        update_btn.setText("Downloading (" + progress.toString() + "%)");

                    }
                    fos.flush()
                    // 下载完成
//                    listener.onDownloadSuccess();
                    i("download success")
                    i(
                        "totalTime=" + (System.currentTimeMillis() - startTime)
                    )
                    update_btn.setText("Installing...");
                    file.install();


                } catch (e: Exception) {
                    e.printStackTrace()
                    //                    listener.onDownloadFailed();
                    i("download failed")
                } finally {
                    try {
                        if (`is` != null) `is`.close()
                    } catch (e: IOException) {
                    }
                    try {
                        if (fos != null) fos.close()
                    } catch (e: IOException) {
                    }
                }
            }


        })
    }
}