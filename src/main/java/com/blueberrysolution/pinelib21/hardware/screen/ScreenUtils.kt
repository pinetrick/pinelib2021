package com.blueberrysolution.pinelib21.hardware.screen

import android.view.WindowManager
import com.blueberrysolution.pinelib21.app.app
import com.blueberrysolution.pinelib21.context.a

fun keepScreenOn(needOn: Boolean){
    if (needOn){
        a().getWindow().addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
    else{
        a().getWindow().clearFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}