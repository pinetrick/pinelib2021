package com.blueberrysolution.pinelib21.view.message_box

import com.blueberrysolution.pinelib21.R
import com.blueberrysolution.pinelib21.app.c
import com.blueberrysolution.pinelib21.basic_class_ext.helper_functions.s
import com.blueberrysolution.pinelib21.context.a

open class MessageBox {
    var messageBoxObj: MessageBoxObj = MessageBoxObj();

    constructor(){

    }

    fun show(): MessageBox{

        if (!MessageBoxManager.messageboxQueue.contains(this)){
            MessageBoxManager.messageboxQueue.add(this);
        }

        if (MessageBoxManager.messageBoxShowing == null) {
            messageBoxObj.dialog = MessageBoxView(a(), R.style.dialog, messageBoxObj, this);
            messageBoxObj.dialog!!.show()

            MessageBoxManager.messageBoxShowing = this;
        }
        return this;
    }

    fun setMessageBoxStyle(RLayout: Int): MessageBox{
        messageBoxObj.baseLayout = RLayout;
        return this;
    }

    fun setOnBtnClickListener(listener: (idFrom1: Int, input: String) -> Unit): MessageBox{
        messageBoxObj.callback = listener;
        return this;
    }

    fun setCancelable(cancelable: Boolean): MessageBox{
        messageBoxObj.cancelable = cancelable;
        return this;
    }

//    fun show(id: Int, vararg btns: Int): MessageBox{
//        var btns_m: Array<out String> = arrayOf<String>()
//        btns.forEach {
//            btns_m.plus(s(it))
//        }
//        return show(s(id), btns_m);
//    }

    fun setInputAllow(allow: Boolean, defValue: String = "", hint: String = ""): MessageBox{
        messageBoxObj.allowInput = allow;
        messageBoxObj.hint = hint;
        messageBoxObj.inputDefValue = defValue;
        return this;
    }

    fun show(title: String, vararg btns: String): MessageBox{
        messageBoxObj.title = title;
        messageBoxObj.btns = btns;
        return show();
    }

    fun hide(){
        if (messageBoxObj.dialog != null){
            messageBoxObj.dialog!!.dismiss()
        }
    }



}