package com.blueberrysolution.pinelib21.context

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.blueberrysolution.pinelib21.R
import com.blueberrysolution.pinelib21.addone.mytimer.MyTimer
import com.blueberrysolution.pinelib21.app.C
import com.blueberrysolution.pinelib21.basic_class_ext.helper_functions.s
import com.blueberrysolution.pinelib21.hardware.screen.keepScreenOn
import com.blueberrysolution.pinelib21.view.toast.t


fun a(): PineActivity {
    return PineActivity.activity!!
}

open class PineActivity : AppCompatActivity() {
    //是否开启双击退出
    open var enableDoubleReturnExit = false


    override fun onCreate(savedInstanceState: Bundle?) {
        PineActivity.activity = this;
        super.onCreate(savedInstanceState)

        keepScreenOn(C.keepScreenOn);

    }

    override fun onResume() {
        PineActivity.activity = this;
        super.onResume()
    }

    //返回键按下事件，可被重写
    open fun onReturnKeyDown(): Boolean {
        return false;
    }

    private var mBackKeyPressed = false
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {

                if (enableDoubleReturnExit) {
                    if (!mBackKeyPressed) {
                        t(s(R.string.one_more_time_to_exit))
                        mBackKeyPressed = true
                        MyTimer.i().setInterval(2000).setOnTimerThreadListener {
                            mBackKeyPressed = false
                        }.startOnce()
                        return false;
                    } else {//退出程序
                        this.finish()
                        //System.exit(0)
                    }
                }

                if (onReturnKeyDown())
                    return false;
            }
            else -> {
            }
        }


        return super.onKeyDown(keyCode, event)
    }

    fun 状态栏透明(blackWords: Boolean = true) {
        //透明状态栏
        if (Build.VERSION.SDK_INT >= 21) { //21表示5.0
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= 19) { //19表示4.4
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        val decor = activity!!.window.decorView
        if (blackWords) {
            decor.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decor.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }

    companion object {
        var activity: PineActivity? = null;
    }

}