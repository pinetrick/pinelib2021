package com.blueberrysolution.pinelib21.view.waitting_box

import com.blueberrysolution.pinelib21.R
import com.blueberrysolution.pinelib21.context.a
import com.blueberrysolution.pinelib21.debug.d
import com.blueberrysolution.pinelib21.debug.e
import java.lang.Exception


class WaittingBox {
    var waittingBoxObj: WaittingBoxObj = WaittingBoxObj();

    constructor() {

    }

    fun show(): WaittingBox {


        waittingBoxObj.dialog = WaittingBoxView(
            a(),
            R.style.dialog,
            waittingBoxObj,
            this
        );
        try{
            waittingBoxObj.dialog!!.show()
        }
        catch (ee: Exception){
            e("上下文失效，无法显示等待框")
        }


        return this;
    }

    fun setWaittingBoxStyle(RLayout: Int): WaittingBox {
        waittingBoxObj.baseLayout = RLayout;
        return this;
    }

    fun setOnCancelListener(listener: () -> Unit): WaittingBox {
        waittingBoxObj.onCancel = listener;
        return this;
    }

    fun setCancelable(cancelable: Boolean): WaittingBox {
        waittingBoxObj.cancelable = cancelable;
        return this;
    }




    fun show(title: String): WaittingBox {
        waittingBoxObj.title = title;
        return show();
    }

    fun hide() {
        if (waittingBoxObj.dialog != null) {
            if (waittingBoxObj.dialog!!.isShowing) {
                waittingBoxObj.dialog!!.dismiss()
            }
        }
    }


}