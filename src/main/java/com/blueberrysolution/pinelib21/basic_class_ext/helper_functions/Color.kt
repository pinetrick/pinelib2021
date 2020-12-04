package com.blueberrysolution.pinelib21.basic_class_ext.helper_functions

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.content.ContextCompat
import com.blueberrysolution.pinelib21.app.c

fun color(id: Int): Int {
    var version = Build.VERSION.SDK_INT;
    if (version >= 23) {
        return ContextCompat.getColor(c(), id)
    } else {
        return c().resources.getColor(id);
    }
}