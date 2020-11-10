package com.blueberrysolution.pinelib21.view.waitting_box

import com.blueberrysolution.pinelib21.R
import com.blueberrysolution.pinelib21.context.a


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
        waittingBoxObj.dialog!!.show()

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
            waittingBoxObj.dialog!!.dismiss()
        }
    }


}