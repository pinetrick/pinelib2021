package com.blueberrysolution.pinelib21.context

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blueberrysolution.pinelib21.app.C
import com.blueberrysolution.pinelib21.hardware.screen.keepScreenOn


fun a(): PineActivity{
    return PineActivity.activity!!
}

open class PineActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        PineActivity.activity = this;
        super.onCreate(savedInstanceState)

        keepScreenOn(C.keepScreenOn);

    }

    override fun onResume() {
        PineActivity.activity = this;
        super.onResume()
    }

    companion object{
        var activity: PineActivity? = null;
    }
}