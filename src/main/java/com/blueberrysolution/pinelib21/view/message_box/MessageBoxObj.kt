package com.blueberrysolution.pinelib21.view.message_box

import com.blueberrysolution.pinelib21.R
import com.blueberrysolution.pinelib21.basic_class_ext.helper_functions.s

class MessageBoxObj {
    var title = s(R.string.message_box_title);
    var hint = "";
    var inputDefValue = "";

    var callback: ((idFrom1: Int, input: String) -> Unit)? = null;

    var baseLayout: Int = R.layout.message_box_black_normal
    var dialog: MessageBoxView? = null;

    var cancelable: Boolean = false;

    lateinit var btns: Array<out String>;

    var allowInput: Boolean = false;

}