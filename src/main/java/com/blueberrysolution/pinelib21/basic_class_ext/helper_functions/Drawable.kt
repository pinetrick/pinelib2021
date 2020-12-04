package com.blueberrysolution.pinelib21.basic_class_ext.helper_functions

import android.graphics.drawable.Drawable
import com.blueberrysolution.pinelib21.app.c

fun drawable(id: Int): Drawable{
    return c().getDrawable(id)!!;
}