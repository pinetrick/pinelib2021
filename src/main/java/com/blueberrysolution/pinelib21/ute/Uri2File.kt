package com.blueberrysolution.pinelib21.ute

import android.content.ContentResolver
import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.FileUtils
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import androidx.loader.content.CursorLoader
import com.blueberrysolution.pinelib21.app.c
import com.blueberrysolution.pinelib21.debug.d
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.random.Random.Default.nextInt

//本文件无效 可以删除
object Uri2File {
    fun getFileByUri(uri: Uri): File? {
        var context = c();

        var path: String? = null
        if ("file" == uri.getScheme()) {
            path = uri.getEncodedPath()
            if (path != null) {
                path = Uri.decode(path)
                val cr: ContentResolver = context.getContentResolver()
                val buff = StringBuffer()
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                    .append("'$path'").append(")")
                val cur: Cursor = cr.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    arrayOf(
                        MediaStore.Images.ImageColumns._ID,
                        MediaStore.Images.ImageColumns.DATA
                    ),
                    buff.toString(),
                    null,
                    null
                )!!
                var index = 0
                var dataIdx = 0
                cur.moveToFirst()
                while (!cur.isAfterLast()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID)
                    index = cur.getInt(index)
                    dataIdx = cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                    path = cur.getString(dataIdx)
                    cur.moveToNext()
                }
                cur.close()
                if (index == 0) {
                } else {
                    val u: Uri = Uri.parse("content://media/external/images/media/$index")
                    println("temp uri is :$u")
                }
            }
            if (path != null) {
                return File(path)
            }
        } else if ("content" == uri.getScheme()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                var file = uriToFileQ(uri)
                return file
            } else {
                var file = getFilePathByUri(uri)
                return File(file);
            }

        } else {
            //Log.i(TAG, "Uri Scheme:" + uri.getScheme());
        }
        return null
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun uriToFileQ(uri: Uri): File? {
        var context = c();
        if (uri.scheme == ContentResolver.SCHEME_FILE)
            File(requireNotNull(uri.path))
        else if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            //把文件保存到沙盒
            val contentResolver = context.contentResolver
            val displayName = run {
                val cursor = contentResolver.query(uri, null, null, null, null)
                cursor?.let {
                    if (it.moveToFirst())
                        it.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    else null
                }
            } ?: "${System.currentTimeMillis()}${
            Random().nextInt(9999)
            }.${MimeTypeMap.getSingleton()
                .getExtensionFromMimeType(contentResolver.getType(uri))}"
            val ios = contentResolver.openInputStream(uri)
            if (ios != null) {
                File("${context.externalCacheDir!!.absolutePath}/$displayName")
                    .apply {
                        val fos = FileOutputStream(this)

                        FileUtils.copy(ios, fos)

                        fos.close()
                        ios.close()
                    }
            } else null
        } else null
        return null
    }


    fun getFilePathByUri(uri: Uri): String? {
        // 以 file:// 开头的
        if (ContentResolver.SCHEME_FILE == uri.scheme) {
            return uri.path
        }
        // 以/storage开头的也直接返回
        if (isOtherDocument(uri)) {
            return uri.path
        }
        // 版本兼容的获取！
        var path = getFilePathByUri_BELOWAPI11(uri)
        if (path != null) {
            d("getFilePathByUri_BELOWAPI11获取到的路径为：$path")
            return path
        }
        path = getFilePathByUri_API11to18(uri)
        if (path != null) {
            d("getFilePathByUri_API11to18获取到的路径为：$path")
            return path
        }
        path = getFilePathByUri_API19(uri)
        d("getFilePathByUri_API19获取到的路径为：$path")
        return path
    }

    private fun getFilePathByUri_BELOWAPI11(uri: Uri): String? {
        try {
            // 以 content:// 开头的，比如 content://media/extenral/images/media/17766
            if (ContentResolver.SCHEME_CONTENT == uri.scheme) {
                var path: String? = null
                val projection =
                    arrayOf(MediaStore.Images.Media.DATA)
                val cursor: Cursor =
                    c().getContentResolver().query(uri, projection, null, null, null)!!
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        val columnIndex =
                            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                        if (columnIndex > -1) {
                            path = cursor.getString(columnIndex)
                        }
                    }
                    cursor.close()
                }
                return path
            }
        } catch (e: java.lang.Exception) {
            return null;
        }
        return null
    }

    private fun getFilePathByUri_API11to18(contentUri: Uri): String? {
        try {
            val projection =
                arrayOf(MediaStore.Images.Media.DATA)
            var result: String? = null
            val cursorLoader =
                CursorLoader(c(), contentUri, projection, null, null, null)
            val cursor: Cursor = cursorLoader.loadInBackground()!!
            if (cursor != null) {
                val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                result = cursor.getString(column_index)
                cursor.close()
            }
            return result
        } catch (e: java.lang.Exception) {
            return null;
        }
    }

    private fun getFilePathByUri_API19(uri: Uri): String? {
        // 4.4及之后的 是以 content:// 开头的，比如 content://com.android.providers.media.documents/document/image%3A235700
        if (ContentResolver.SCHEME_CONTENT == uri.scheme && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(c(), uri)) {
                if (isExternalStorageDocument(uri)) {
                    // ExternalStorageProvider
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split =
                        docId.split(":".toRegex()).toTypedArray()
                    val type = split[0]
                    if ("primary".equals(type, ignoreCase = true)) {
                        return if (split.size > 1) {
                            Environment.getExternalStorageDirectory()
                                .toString() + "/" + split[1]
                        } else {
                            Environment.getExternalStorageDirectory()
                                .toString() + "/"
                        }
                        // This is for checking SD Card
                    }
                } else if (isDownloadsDocument(uri)) {
                    //下载内容提供者时应当判断下载管理器是否被禁用
                    val stateCode: Int = c().getPackageManager()
                        .getApplicationEnabledSetting("com.android.providers.downloads")
                    if (stateCode != 0 && stateCode != 1) {
                        return null
                    }
                    var id = DocumentsContract.getDocumentId(uri)
                    // 如果出现这个RAW地址，我们则可以直接返回!
                    if (id.startsWith("raw:")) {
                        return id.replaceFirst("raw:".toRegex(), "")
                    }
                    if (id.contains(":")) {
                        val tmp =
                            id.split(":".toRegex()).toTypedArray()
                        if (tmp.size > 1) {
                            id = tmp[1]
                        }
                    }
                    var contentUri =
                        Uri.parse("content://downloads/public_downloads")
                    d("测试打印Uri: $uri")
                    try {
                        contentUri = ContentUris.withAppendedId(contentUri, id.toLong())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    var path = getDataColumn(contentUri, null, null)
                    if (path != null) return path
                    // 兼容某些特殊情况下的文件管理器!
                    val fileName = getFileNameByUri(uri)
                    if (fileName != null) {
                        path = Environment.getExternalStorageDirectory()
                            .toString() + "/Download/" + fileName
                        return path
                    }
                } else if (isMediaDocument(uri)) {
                    // MediaProvider
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split =
                        docId.split(":".toRegex()).toTypedArray()
                    val type = split[0]
                    var contentUri: Uri? = null
                    if ("image" == type) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    } else if ("video" == type) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    } else if ("audio" == type) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                    val selection = "_id=?"
                    val selectionArgs =
                        arrayOf(split[1])
                    return getDataColumn(contentUri, selection, selectionArgs)
                }
            }
        }
        return null
    }

    private fun getFileNameByUri(uri: Uri): String? {
        var relativePath = getFileRelativePathByUri_API18(uri)
        if (relativePath == null) relativePath = ""
        val projection = arrayOf(
            MediaStore.MediaColumns.DISPLAY_NAME
        )
        c().getContentResolver().query(uri, projection, null, null, null).use({ cursor ->
            if (cursor != null && cursor.moveToFirst()) {
                val index: Int = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
                return relativePath + cursor.getString(index)
            }
        })
        return null
    }

    private fun getFileRelativePathByUri_API18(uri: Uri): String? {
        val projection: Array<String>
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            projection = arrayOf(
                MediaStore.MediaColumns.RELATIVE_PATH
            )
            c().getContentResolver().query(uri, projection, null, null, null).use({ cursor ->
                if (cursor != null && cursor.moveToFirst()) {
                    val index: Int =
                        cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.RELATIVE_PATH)
                    return cursor.getString(index)
                }
            })
        }
        return null
    }

    private fun getDataColumn(
        uri: Uri?,
        selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        val column = MediaStore.Images.Media.DATA
        val projection = arrayOf(column)
        try {
            c().getContentResolver().query(uri!!, projection, selection, selectionArgs, null)
                .use { cursor ->
                    if (cursor != null && cursor.moveToFirst()) {
                        val column_index: Int = cursor.getColumnIndexOrThrow(column)
                        return cursor.getString(column_index)
                    }
                }
        } catch (iae: IllegalArgumentException) {
            iae.printStackTrace()
        }
        return null
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    private fun isOtherDocument(uri: Uri?): Boolean {
        // 以/storage开头的也直接返回
        if (uri != null && uri.path != null) {
            val path = uri.path
            if (path!!.startsWith("/storage")) {
                return true
            }
            if (path.startsWith("/external_files")) {
                return true
            }
        }
        return false
    }

    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }


}