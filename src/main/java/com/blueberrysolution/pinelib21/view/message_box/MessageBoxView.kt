package com.blueberrysolution.pinelib21.view.message_box

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.blueberrysolution.pinelib21.R
import com.blueberrysolution.pinelib21.app.app
import com.blueberrysolution.pinelib21.app.c
import com.blueberrysolution.pinelib21.basic_class_ext.input_method.imeHideImmediately
import com.blueberrysolution.pinelib21.basic_class_ext.input_method.imeShow
import com.blueberrysolution.pinelib21.context.a
import com.blueberrysolution.pinelib21.view.toast.t


class MessageBoxView: AlertDialog {
    var messageBoxObj: MessageBoxObj = MessageBoxObj();
    var messageBox: MessageBox? = null;
    var editTextX: EditText? = null;
    var editLayoutX: View? = null;

    constructor(context: Context, theme: Int, messageBoxObj: MessageBoxObj, messageBox: MessageBox) : super(context, theme) {
        this.messageBoxObj = messageBoxObj;
        this.messageBox = messageBox;
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


        setContentView(messageBoxObj.baseLayout)

        //强制Dialogqia
        val windowManager = a().getWindowManager()
        val display = windowManager.defaultDisplay
        var lp = window!!.attributes

        window!!.attributes = lp;


        setCanceledOnTouchOutside(messageBoxObj.cancelable)// 设置点击Dialog外部任意区域关闭Dialog

        setupTitle(messageBoxObj.title)
        setButton(messageBoxObj.btns)
        setInput(messageBoxObj.allowInput, messageBoxObj.inputDefValue, messageBoxObj.hint)
        setInputMethod()
        setCloseBtn()
    }

    private fun setCloseBtn() {
        val string2Id: Int = a().getResources()
            .getIdentifier("close_btn", "id", app().getPackageName()) //获取标识符
        var closeBtn = findViewById<ImageView>(string2Id)
        closeBtn?.setOnClickListener {
            messageBoxObj.dialog?.dismiss()
        }
    }

    private fun setInputMethod() {
        if(messageBoxObj.allowInput) {
             messageBoxObj.dialog!!.imeShow()

            //弹出输入法
            if (editTextX != null) {
                //editTextX!!.imeShow()
                //选中全部内容
                editTextX!!.selectAll()
            }
        }

    }

    fun setInput(allowInput: Boolean, inputDefValue:String, hint: String) {
        val editText: Int = a().getResources()
            .getIdentifier("editText", "id", app().getPackageName()) //获取标识符
        val editLayout: Int = a().getResources()
            .getIdentifier("editLayout", "id", app().getPackageName()) //获取标识符


        if (editText != 0){
            editTextX = findViewById<EditText>(editText)
            if (editTextX != null) {
                editTextX!!.visibility = View.GONE
            }
        }

        if (editLayout != 0){
            editLayoutX = findViewById<View>(editLayout)
            if (editLayoutX != null) {
                editLayoutX!!.visibility = View.GONE
            }
        }

        if (allowInput){
            if (editTextX != null) {
                editTextX!!.hint = hint;
                editTextX!!.setText(inputDefValue);
                editTextX!!.visibility = View.VISIBLE
            }
            if  (editLayoutX != null) {
                editLayoutX!!.visibility = View.VISIBLE
            }
        }

    }

    fun setOneButton(key: String, value: String?, id: Int){
        val string2Id: Int = a().getResources()
            .getIdentifier(key, "id", app().getPackageName()) //获取标识符
        if (string2Id != 0) {
            setOneButton(string2Id, value, id)
        }
        else{
            t("信息窗口按钮不足，检查布局")
        }
    }

    fun setOneButton(key: Int, value: String?, id: Int){
        setOneButton(key, key, value, id)
    }

    fun setOneButton(key: Int, layout: Int, value: String?, id: Int){
        var btn = findViewById<TextView>(key)
        var layoutx = findViewById<View>(layout)
        if ((value != null) && (value != "")){
            layoutx.visibility = View.VISIBLE
            btn.text = value;
        }

        layoutx.setOnClickListener{

            if (messageBoxObj.callback != null){

                var string = "";
                if (editTextX != null){
                    string = editTextX!!.text.toString();
                    messageBoxObj.dialog!!.window!!.addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                    messageBoxObj.dialog!!.imeHideImmediately()
                    //layoutx!!.imeHideImmediately()

                };

                messageBoxObj.callback!!(id, string)
            }
            dismiss()
        }
    }

    fun setViewInvisable(key: String){
        val string2Id: Int = a().getResources()
            .getIdentifier(key, "id", app().getPackageName()) //获取标识符
        if (string2Id != 0) {
            var btn = findViewById<View>(string2Id)
            if (btn != null)
                btn.visibility = View.GONE
        }
    }

    fun setButton(btns: Array<out String>){
        for(id in 1 .. 10){
            setViewInvisable("btn" + id)
        }

        var id = 0;
        btns.forEach {
            id++;
            setOneButton("btn" + id, it, id)
        }


    }



    fun setupTitle(title: String){
        var mainMassage = this.findViewById<TextView>(R.id.mainMassage)
        if (mainMassage != null)
            mainMassage.text = title;
    }

    override fun dismiss() {


        MessageBoxManager.messageBoxShowing = null;
        MessageBoxManager.messageboxQueue.remove(messageBox);

        if (MessageBoxManager.messageboxQueue.count() != 0){
            MessageBoxManager.messageboxQueue[0].show()
        }


        super.dismiss()
    }
}
