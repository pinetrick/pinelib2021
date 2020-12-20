package com.blueberrysolution.pinelib21.view.checkbox

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.blueberrysolution.pinelib21.R
import com.blueberrysolution.pinelib21.basic_class_ext.helper_functions.drawable

@SuppressLint("AppCompatCustomView")
class MyCheckBox: CheckBox {

    var checkedBackground = R.drawable.check_box_checked
    var uncheckedBackground = R.drawable.check_box_unchecked

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        init()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        checkedBackground = R.drawable.check_box_checked
        uncheckedBackground = R.drawable.check_box_unchecked
        setButtonDrawable(0)
        setChecked(false)
    }

    fun setCheckedDrawable(checkedBackground: Int){
        this.checkedBackground = checkedBackground;
    }

    fun setUncheckedDrawable(uncheckedBackground: Int){
        this.uncheckedBackground = uncheckedBackground;
    }

    override fun setChecked(checked: Boolean) {


        if (checked){
            if (checkedBackground != 0)
                setBackgroundResource(checkedBackground)
        }
        else{
            if (uncheckedBackground != 0)
                setBackgroundResource(uncheckedBackground)
        }

        super.setChecked(checked)


    }


}