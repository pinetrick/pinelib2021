package com.blueberrysolution.pinelib21.view.zoom_view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.RectF
import android.net.Uri
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.OnScaleGestureListener
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import com.facebook.drawee.view.SimpleDraweeView


/**
 * @author DragonJiang
 * @Date 2016/5/10
 * @Time 11:12
 * @description
 */
class ZoomableDraweeView : SimpleDraweeView {
    private var mScaleDetector: ScaleGestureDetector? = null
    private var mGestureDetector: GestureDetector? = null
    private var mCurrentScale = 1f
    private var mCurrentMatrix: Matrix? = null
    private var mMidX = 0f
    private var mMidY = 0f
    private var mClickListener: OnClickListener? = null

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
        mCurrentMatrix = Matrix()
        val scaleListener: OnScaleGestureListener = object : SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                val scaleFactor = detector.scaleFactor
                mCurrentScale *= scaleFactor
                if (mMidX == 0f) {
                    mMidX = width / 2f
                }
                if (mMidY == 0f) {
                    mMidY = height / 2f
                }
                mCurrentMatrix!!.postScale(scaleFactor, scaleFactor, mMidX, mMidY)
                invalidate()
                return true
            }

            override fun onScaleEnd(detector: ScaleGestureDetector) {
                super.onScaleEnd(detector)
                if (mCurrentScale < 1f) {
                    reset()
                }
                checkBorder()
            }
        }
        mScaleDetector = ScaleGestureDetector(context, scaleListener)
        val gestureListener: SimpleOnGestureListener = object : SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                if (mClickListener != null) {
                    mClickListener!!.onClick()
                }
                return true
            }

            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                if (mCurrentScale > 1f) {
                    mCurrentMatrix!!.postTranslate(-distanceX, -distanceY)
                    invalidate()
                    checkBorder()
                }
                return true
            }
        }
        mGestureDetector = GestureDetector(context, gestureListener)
    }

    /**
     * 检查图片边界是否移到view以内
     * 目的是让图片边缘不要移动到view里面
     */
    private fun checkBorder() {
        val rectF = getDisplayRect(mCurrentMatrix)
        var reset = false
        var dx = 0f
        var dy = 0f
        if (rectF.left > 0) {
            dx = left - rectF.left
            reset = true
        }
        if (rectF.top > 0) {
            dy = top - rectF.top
            reset = true
        }
        if (rectF.right < right) {
            dx = right - rectF.right
            reset = true
        }
        if (rectF.bottom < height) {
            dy = height - rectF.bottom
            reset = true
        }
        if (reset) {
            mCurrentMatrix!!.postTranslate(dx, dy)
            invalidate()
        }
    }

    /**
     * Helper method that maps the supplied Matrix to the current Drawable
     *
     * @param matrix - Matrix to map Drawable against
     * @return RectF - Displayed Rectangle
     */
    private fun getDisplayRect(matrix: Matrix?): RectF {
        val rectF = RectF(
            left.toFloat(),
            top.toFloat(),
            right.toFloat(),
            bottom.toFloat()
        )
        matrix!!.mapRect(rectF)
        return rectF
    }

    override fun setImageURI(uri: Uri?) {
        reset()
        super.setImageURI(uri)
    }

    override fun setImageBitmap(bm: Bitmap) {
        reset()
        super.setImageBitmap(bm)
    }

    override fun onDraw(canvas: Canvas) {
        val saveCount = canvas.save()
        canvas.concat(mCurrentMatrix)
        super.onDraw(canvas)
        canvas.restoreToCount(saveCount)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mScaleDetector!!.onTouchEvent(event)
        if (!mScaleDetector!!.isInProgress) {
            mGestureDetector!!.onTouchEvent(event)
        }
        return true
    }

    /**
     * Resets the zoom of the attached image.
     * This has no effect if the image has been destroyed
     */
    public fun reset() {
        mCurrentMatrix!!.reset()
        mCurrentScale = 1f
        invalidate()
    }

    interface OnClickListener {
        fun onClick()
    }

    fun setOnClickListener(listener: OnClickListener?) {
        mClickListener = listener
    }
}