package com.blueberrysolution.pinelib21.basic_class_ext.input_method

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import com.blueberrysolution.pinelib21.addone.mytimer.MyTimer
import com.blueberrysolution.pinelib21.app.c
import com.blueberrysolution.pinelib21.context.a
import com.blueberrysolution.pinelib21.debug.e


fun EditText.imeShow() {
    MyTimer.i().setInterval(100).setOnTimerUIListener {
        imeShowImmediately()
    }.startOnce()
}

fun EditText.imeShowImmediately(){
    val inputMethodManager =
        c().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    inputMethodManager!!.showSoftInput(
        this,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
}

fun View.imeHideImmediately(){
    a().runOnUiThread {
        val inputMethodManager =
            c().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        if (a().currentFocus != null) {
            inputMethodManager!!.hideSoftInputFromWindow(
                a().currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
        else{
            //window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            inputMethodManager!!.hideSoftInputFromWindow(this.windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
            e("无法获取输入法焦点，故无法关闭软键盘")
        }
    }

}
fun Dialog.imeShow(){
    window!!.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
}

fun Dialog.imeHideImmediately(){
    a().runOnUiThread {
        val inputMethodManager =
            c().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        if (a().currentFocus != null) {
            inputMethodManager!!.hideSoftInputFromWindow(
                a().currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
        else{

            inputMethodManager!!.hideSoftInputFromWindow(
                this.currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
            e("无法获取输入法焦点，故无法关闭软键盘")
        }
    }

}

//获取输入法打开的状态
fun EditText.isImeShowing(): Boolean {
    val imm =
        c().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return imm.isActive //isOpen若返回true，则表示输入法打开
}

