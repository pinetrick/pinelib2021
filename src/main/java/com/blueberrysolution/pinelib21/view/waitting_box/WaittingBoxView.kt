package com.blueberrysolution.pinelib21.view.waitting_box


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import com.blueberrysolution.pinelib21.R
import com.blueberrysolution.pinelib21.app.app
import com.blueberrysolution.pinelib21.app.c
import com.blueberrysolution.pinelib21.context.a
import com.blueberrysolution.pinelib21.view.toast.t


class WaittingBoxView : AlertDialog {
    var waittingBoxObj: WaittingBoxObj = WaittingBoxObj();
    var waittingBox: WaittingBox? = null;

    constructor(
        context: Context,
        theme: Int,
        waittingBoxObj: WaittingBoxObj,
        waittingBox: WaittingBox
    ) : super(context, theme) {
        this.waittingBoxObj = waittingBoxObj;
        this.waittingBox = waittingBox;
    }


    protected constructor(context: Context) : super(context) {
    }


    protected constructor(
        context: Context, cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener
    ) : super(context, cancelable, cancelListener) {
        // TODO Auto-generated constructor stub
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //强制Dialog全屏
        val resources = c().getResources()
        val dm = resources.getDisplayMetrics()

        window!!.attributes.width = dm.widthPixels


        setContentView(waittingBoxObj.baseLayout)

        //强制Dialogqia
        val windowManager = a().getWindowManager()
        val display = windowManager.defaultDisplay
        var lp = window!!.attributes

        window!!.attributes = lp;


        setCanceledOnTouchOutside(waittingBoxObj.cancelable)// 设置点击Dialog外部任意区域关闭Dialog

        setupTitle(waittingBoxObj.title)



    }




    fun setupTitle(title: String) {
        var mainMassage = this.findViewById<TextView>(R.id.mainMassage)
        mainMassage.text = title;
    }

    override fun dismiss() {

        super.dismiss()
    }
}
