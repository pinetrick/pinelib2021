package com.blueberrysolution.pinelib21.context

import android.os.Bundle
import android.view.KeyEvent
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


    companion object {
        var activity: PineActivity? = null;
    }

}