package com.blueberrysolution.pinelib21.addone.shared_preferences

import com.blueberrysolution.pinelib21.net.retrofit.SerializableClass
import com.google.gson.Gson
import java.util.*
import kotlin.reflect.KClass

//请使用普通类，不要继承乱七八糟的
fun spSave(key: String, any: Any){
    var 序列化结果 = Gson().toJson(any)
    Sp.i.put(key, 序列化结果)
}

//请使用普通类，不要继承乱七八糟的
fun <T : Any> spGet(key: String, classOfT: KClass<T>): T{
    var 结果 = Sp.i.get(key, "");
    if (结果 != ""){
        return Gson().fromJson<T>(结果, classOfT.java)
    }
    else{
        return classOfT.java.newInstance()
    }


}

