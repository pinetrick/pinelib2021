package com.blueberrysolution.pinelib21.view.waitting_box

import com.blueberrysolution.pinelib21.R
import com.blueberrysolution.pinelib21.basic_class_ext.helper_functions.s
import com.blueberrysolution.pinelib21.view.message_box.MessageBoxView

class WaittingBoxObj {
    var title = s(R.string.waitting_box_title);

    var onCancel: (() -> Unit)? = null;

    var baseLayout: Int = R.layout.waitting_box_black
    var dialog: WaittingBoxView? = null;

    var cancelable: Boolean = false;



}