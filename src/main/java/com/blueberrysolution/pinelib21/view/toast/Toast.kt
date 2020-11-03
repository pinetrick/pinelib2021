package com.blueberrysolution.pinelib21.view.toast

import android.util.Log
import android.widget.Toast
import com.blueberrysolution.pinelib21.app.app
import com.blueberrysolution.pinelib21.basic_class_ext.helper_functions.s

fun t(id: Int){
    Toast.makeText(app(), s(id), Toast.LENGTH_LONG).show()
}

fun t(s: Any){
    Toast.makeText(app(), s.toString(), Toast.LENGTH_LONG).show()
}