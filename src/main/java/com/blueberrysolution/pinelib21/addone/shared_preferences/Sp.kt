package com.blueberrysolution.pinelib21.addone.shared_preferences


import android.text.method.TextKeyListener.clear
import android.R.id.edit
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.blueberrysolution.pinelib21.app.c


class Sp(FILE_NAME: String){
    lateinit var sharedPreferences: SharedPreferences
    /*
     * 保存手机里面的名字
     */
    lateinit var editor: SharedPreferences.Editor

    init  {
        sharedPreferences = c().getSharedPreferences(
            FILE_NAME,
            Context.MODE_PRIVATE
        )
        editor = sharedPreferences.edit()
    }

    /**
     * 存储
     */
    fun put(key: String, `object`: Any) {
        if (`object` is String) {
            editor.putString(key, `object`)
        } else if (`object` is Int) {
            editor.putInt(key, `object`)
        } else if (`object` is Boolean) {
            editor.putBoolean(key, `object`)
        } else if (`object` is Float) {
            editor.putFloat(key, `object`)
        } else if (`object` is Long) {
            editor.putLong(key, `object`)
        } else {
            editor.putString(key, `object`.toString())
        }
        editor.commit()
    }

    /**
     * 获取保存的数据
     */
    fun getV(key: String, defaultObject: Any): Any? {
        return if (defaultObject is String) {
            sharedPreferences.getString(key, defaultObject)
        } else if (defaultObject is Int) {
            sharedPreferences.getInt(key, defaultObject)
        } else if (defaultObject is Boolean) {
            sharedPreferences.getBoolean(key, defaultObject)
        } else if (defaultObject is Float) {
            sharedPreferences.getFloat(key, defaultObject)
        } else if (defaultObject is Long) {
            sharedPreferences.getLong(key, defaultObject)
        } else {
            sharedPreferences.getString(key, null)
        }
    }

    fun get(key: String, defaultObject: String = ""): String{
        var r = getV(key, defaultObject);
        if (r is String)
            return r
        else
            return defaultObject
    }

    fun get(key: String, defaultObject: Int = 0): Int{
        var r = getV(key, defaultObject);
        if (r is Int)
            return r
        else
            return defaultObject
    }

    fun get(key: String, defaultObject: Boolean = true): Boolean{
        var r = getV(key, defaultObject);
        if (r is Boolean)
            return r
        else
            return defaultObject
    }

    fun get(key: String, defaultObject: Float = 0f): Float{
        var r = getV(key, defaultObject);
        if (r is Float)
            return r
        else
            return defaultObject
    }

    fun get(key: String, defaultObject: Long = 0): Long{
        var r = getV(key, defaultObject);
        if (r is Long)
            return r
        else
            return defaultObject
    }
    /**
     * 移除某个key值已经对应的值
     */
    fun remove(key: String) {
        editor.remove(key)
        editor.commit()
    }

    /**
     * 清除所有数据
     */
    fun clear() {
        editor.clear()
        editor.commit()
    }

    /**
     * 查询某个key是否存在
     */
    fun contain(key: String): Boolean {
        return sharedPreferences.contains(key)!!
    }

    /**
     * 返回所有的键值对
     */
    fun getAll(): Map<String, *> {
        return sharedPreferences.all
    }

    companion object {
        val i: Sp by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED)
        {
            Sp("pine_lib_share_preferences");
        }
    }
}