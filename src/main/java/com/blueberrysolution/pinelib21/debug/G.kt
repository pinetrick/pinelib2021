package com.blueberrysolution.pinelib21.debug

import android.util.Log
import com.blueberrysolution.pinelib21.app.C
import com.blueberrysolution.pinelib21.app.app
import com.blueberrysolution.pinelib21.view.toast.t

var tag = "--- PineDebug ---"

fun d(s: Any){
    if (C.isDebug){
        Log.d(tag, s.toString());
    }


}

fun i(s: Any){
    if (C.isDebug){
        Log.i(tag, s.toString());
    }
}

fun w(s: Any){
    Log.w(tag, s.toString());
    if (C.isDebug){
        t(s)
    }
}

fun e(s: Any){
    Log.e(tag, s.toString());
    if (C.isDebug){
        t(s)
    }
}


