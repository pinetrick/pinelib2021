package com.blueberrysolution.pinelib21.net.retrofit


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Modifier
import com.google.gson.FieldAttributes
import com.google.gson.ExclusionStrategy



abstract class SerializableClass {

    var gson: Gson;
    private val EXCLUDE = object : ArrayList<String>() {
        init {
            add("serialVersionUID")
            add("_db")
            add("_fields")
            add("gson")
        }
    }

    constructor(){
        gson = GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC, Modifier.PROTECTED)
            .setLenient()// json宽松
            .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
            .serializeNulls() //智能null
            .setPrettyPrinting()// 调教格式
            .setExclusionStrategies(object : ExclusionStrategy {
                override fun shouldSkipField(f: FieldAttributes): Boolean {
                    var exclude = false
                    try {
                        exclude = EXCLUDE.contains(f.name)
                    } catch (ignore: Exception) {
                    }

                    return exclude
                }

                override fun shouldSkipClass(clazz: Class<*>): Boolean {
                    return false
                }
            })
            .create();
    }



    override fun toString(): String{
        var r = "[error]";
        try {
            r = gson.toJson(this, this::class.java);
        }
        catch (e: Exception){
            //G.e("Json Serialization Error: " + e.toString())
        }

        return r
    }
}