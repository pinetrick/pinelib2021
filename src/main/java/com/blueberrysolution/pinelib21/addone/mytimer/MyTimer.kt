package com.blueberrysolution.pinelib21.addone.mytimer


import android.os.Handler
import android.os.Looper
import java.lang.ref.WeakReference
import java.util.*
import kotlin.concurrent.thread


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MyTimer {
    var interval: Long = 1000;
    var stopImmediate: Boolean = false;
    var allowRun: Boolean = false;

    var onTimerUIListener: (() -> Unit)? = null;
    var onTimerThreadListener: (() -> Unit)? = null;

    var mHandler = Handler(Looper.getMainLooper())

    var r: Runnable = object : Runnable {
        override fun run() {

            //do something
            if (!stopImmediate){
                thread {
                    if (onTimerThreadListener != null)
                        onTimerThreadListener!!();
                }
                if (onTimerUIListener != null)
                    onTimerUIListener!!();
                //每隔1s循环执行run方法
                if (allowRun){
                    mHandler.postDelayed(this, interval)
                }else{
                    stopImmediate = true;
                }
            }


        }
    }

    fun stop(){
        stopImmediate = true;

    }

    fun start(): MyTimer{
        stopImmediate = false;
        allowRun = true;

        mHandler.postDelayed(r, interval)

        return this;
    }

    fun startOnce(): MyTimer{
        stopImmediate = false;
        allowRun = false;


        mHandler.postDelayed(r, interval)

        return this;
    }

    fun setOnTimerUIListener(callback: () -> Unit): MyTimer {
        this.onTimerUIListener = callback;
        return  this;
    }

    fun setOnTimerThreadListener(callback: () -> Unit): MyTimer {
        this.onTimerThreadListener = callback;
        return  this;
    }

    fun setInterval(interval: Long): MyTimer {
        if (interval > 0){
            this.interval = interval;

            if (allowRun){
                mHandler.removeCallbacks(r)
                mHandler.postDelayed(r, interval)
            }
        }

        return  this;
    }

    companion object {
        fun i(): MyTimer {
            return MyTimer();
        }
    }

}
