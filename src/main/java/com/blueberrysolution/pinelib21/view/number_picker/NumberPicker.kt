package com.blueberrysolution.pinelib21.view.number_picker


import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.blueberrysolution.pinelib21.R
import com.blueberrysolution.pinelib21.basic_class_ext.helper_functions.color
import com.blueberrysolution.pinelib21.basic_class_ext.helper_functions.drawable
import com.blueberrysolution.pinelib21.basic_class_ext.isInt


class NumberPicker : RelativeLayout, TextWatcher {
    var max: Int = 99;
    var min: Int = 1;
    var number: Int = 1;
    lateinit var mView: View
    lateinit var decBtn: TextView;
    lateinit var incBtn: TextView;
    lateinit var inputText: EditText;


    fun initView(){
        decBtn = mView.findViewById(R.id.decBtn)
        incBtn = mView.findViewById(R.id.incBtn)
        inputText = mView.findViewById(R.id.inputText)

        decBtn.setOnClickListener(::onDec)
        incBtn.setOnClickListener(::onInc)
        inputText.addTextChangedListener(this)

        refreshView()
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        var text = inputText.text.toString()
        if (text.isInt()){
            number = text.toInt()
        }

    }

    fun onDec(v: View){
        if (number != min) {
            number--;
            refreshView()
        }
    }

    fun onInc(v: View){
        if (number != max) {
            number++;
            refreshView()
        }
    }

    fun refreshView(){
        if (number == min){
            decBtn.background = drawable(R.drawable.button_circle_disable)
            decBtn.setTextColor(color(R.color.ccc))
        }
        else{
            decBtn.background = drawable(R.drawable.button_circle_light_gray)
            decBtn.setTextColor(color(R.color.c333))
        }

        if (number == max){
            incBtn.background = drawable(R.drawable.button_circle_disable)
            incBtn.setTextColor(color(R.color.ccc))
        }
        else{
            incBtn.background = drawable(R.drawable.button_circle_light_gray)
            incBtn.setTextColor(color(R.color.c333))
        }

        inputText.setText( number.toString())

    }



    constructor(context: Context?) : super(context) {
        mView = View.inflate(getContext(), R.layout.number_picker, this)
        initView()
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        mView = View.inflate(getContext(), R.layout.number_picker, this)
        initView()
    }
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        mView = View.inflate(getContext(), R.layout.number_picker, this)
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mView = View.inflate(getContext(), R.layout.number_picker, this)
        initView()
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }




}