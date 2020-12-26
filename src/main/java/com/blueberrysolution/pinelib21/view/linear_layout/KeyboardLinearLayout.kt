package com.blueberrysolution.pinelib21.view.linear_layout

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RelativeLayout


class KeyboardLinearLayout : LinearLayout {
    private var mHasInit = false
    private var mHasKeybord = false
    private var mHeight = 0
    private var mListener: ((Int) -> Unit)? = null

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?) : super(context) {}

    /**
     * set keyboard state listener
     */
    fun setOnkbdStateListener(listener: ((Int) -> Unit)) {
        mListener = listener
    }

    override fun onLayout(
        changed: Boolean,
        l: Int,
        t: Int,
        r: Int,
        b: Int
    ) {
        super.onLayout(changed, l, t, r, b)
        if (!mHasInit) {
            mHasInit = true
            mHeight = b
            if (mListener != null) {
                mListener!!(KEYBOARD_STATE_INIT.toInt())
            }
        } else {
            mHeight = if (mHeight < b) b else mHeight
        }
        if (mHasInit && mHeight > b) {
            mHasKeybord = true
            if (mListener != null) {
                mListener!!(KEYBOARD_STATE_SHOW.toInt())
            }
        }
        if (mHasInit && mHasKeybord && mHeight == b) {
            mHasKeybord = false
            if (mListener != null) {
                mListener!!(KEYBOARD_STATE_HIDE.toInt())
            }
        }
    }



    companion object {
        const val KEYBOARD_STATE_SHOW: Byte = -3
        const val KEYBOARD_STATE_HIDE: Byte = -2
        const val KEYBOARD_STATE_INIT: Byte = -1
    }
}