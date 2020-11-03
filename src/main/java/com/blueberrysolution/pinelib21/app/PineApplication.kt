package com.blueberrysolution.pinelib21.app


import android.content.Context
import androidx.multidex.MultiDexApplication
import com.blueberrysolution.pinelib21.debug.d
import com.blueberrysolution.pinelib21.debug.e
import java.util.*


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