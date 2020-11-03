package com.blueberrysolution.pinelib21.basic_class_ext.helper_functions

import com.blueberrysolution.pinelib21.app.c

fun s(id: Int): String{
    return c().getString(id);
}