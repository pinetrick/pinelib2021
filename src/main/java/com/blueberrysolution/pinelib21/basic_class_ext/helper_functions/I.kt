package com.blueberrysolution.pinelib21.basic_class_ext.helper_functions


import android.content.Intent
import com.blueberrysolution.pinelib21.context.a
import com.blueberrysolution.pinelib21.view.toast.t
import java.lang.Exception
import kotlin.reflect.KClass

fun i(className: KClass<*>): I {
    return  I(className);
}


class I(cls: KClass<*>){

    var intent: Intent = Intent();

    init{
        intent.setClass(a(), cls.java);
    }



    fun show(): I{
        try {
            a().startActivity(intent);
        }
        catch (e: Exception){
            t("Please Add Activity to Manifests")
        }
        return  this;



    }




    fun putExtra(name: String, value: String): I{
        intent.putExtra(name, value);
        return this
    }
    fun putExtra(name: String, value: Int): I{
        intent.putExtra(name, value);
        return this
    }
    fun putExtra(name: String, value: Double): I{
        intent.putExtra(name, value);
        return this
    }
    fun putExtra(name: String, value: Boolean): I{
        intent.putExtra(name, value);
        return this
    }







}


