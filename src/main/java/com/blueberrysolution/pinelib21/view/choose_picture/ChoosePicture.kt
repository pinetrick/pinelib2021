package com.blueberrysolution.pinelib21.view.choose_picture

import android.graphics.Bitmap
import com.blueberrysolution.pinelib21.basic_class_ext.helper_functions.i

class ChoosePicture {
    var allowTakePhoto = true;
    var allowFromGallery = true;
    var allowMultipleChoose = true;

    var urlCallback: ((ArrayList<String>) -> Unit)? = null;
    var bitmapCallback: ((ArrayList<Bitmap>) -> Unit)? = null;

    fun setAllowMultipleChoose(allow: Boolean): ChoosePicture{
        allowMultipleChoose = allow;
        return this;
    }

    fun setAllowTakePhoto(allow: Boolean): ChoosePicture{
        allowTakePhoto = allow;
        return this;
    }

    fun setAllowFromGallery(allow: Boolean): ChoosePicture{
        allowFromGallery = allow;
        return this;
    }

    fun setUrlCallback(callback : (ArrayList<String>) -> Unit): ChoosePicture{
        urlCallback = callback;
        return this;
    }

    fun setBitmapCallback(callback : (ArrayList<Bitmap>) -> Unit): ChoosePicture{
        bitmapCallback = callback;
        return this;
    }

    fun start(): ChoosePicture{
        i(PictureChooseActivity::class).show();
        return this;
    }

    private constructor(){

    }

    companion object{
        var cp: ChoosePicture? = null;
        fun i(): ChoosePicture{
            if (cp == null){
                cp =  ChoosePicture();
            }
            return cp!!;
        }
    }
}