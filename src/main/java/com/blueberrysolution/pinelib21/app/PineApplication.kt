package com.blueberrysolution.pinelib21.app


import android.content.Context
import androidx.multidex.MultiDexApplication
import com.blueberrysolution.pinelib21.debug.d
import com.blueberrysolution.pinelib21.debug.e
import com.blueberrysolution.pinelib21.net.retrofit.RetrofitManager
import com.facebook.drawee.backends.pipeline.Fresco
import java.util.*

/*
* 网络初始化办法
* OnCreate中
* myRs = N(baseUrl, RetrofitServices::class).n()
*
公共方法

var myRs: RetrofitServices? = null;
fun n(): RetrofitServices{
    if (myRs != null) {
        return myRs!!;
    }
    e("No Net Service Inited, 闪退")
    return myRs!!;
}

*
* */

fun app(): PineApplication{
    return PineApplication.i();
}

fun c(): Context{
    return app().applicationContext;
}

open class PineApplication: MultiDexApplication {



    constructor(){
        baseApp = this;
    }



    override fun onCreate() {
        super.onCreate()

        ZSignalCheck();

        //初始化网络图片加载组件
        Fresco.initialize(this);


        if (C.isDebug){
            onDebugInit();
        }else{
            onLiveInit();
        }

    }



    open fun onDebugInit(){
        C.isDebug = true;
        C.keepScreenOn = true;
    }

    open fun onLiveInit(){
        C.isDebug = false;
        C.keepScreenOn = false;
    }


    companion object {
        var baseApp: PineApplication? = null;

        fun i(): PineApplication{
            if (baseApp == null){
                e("严重错误，Application 没有被初始化！");
            }

            return baseApp!!
        }



    }



}