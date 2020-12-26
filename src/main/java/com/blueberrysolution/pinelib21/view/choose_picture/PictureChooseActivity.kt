package com.blueberrysolution.pinelib21.view.choose_picture

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.blueberrysolution.pinelib21.R
import com.blueberrysolution.pinelib21.addone.broadcast.Broadcast
import com.blueberrysolution.pinelib21.context.PineActivity
import com.blueberrysolution.pinelib21.view.waitting_box.WaittingBox
import org.w3c.dom.Text

class PictureChooseActivity: PineActivity() {
    var cancel_button: TextView? = null;
    var from_camera: TextView? = null;
    var from_album: TextView? = null;
    var upper_layout: FrameLayout? = null;
    var total_layout: LinearLayout? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.picture_choose_activity)

        查找按钮()
        设置按钮();

    }

    private fun 查找按钮() {
        cancel_button = findViewById<TextView>(R.id.cancel_button)
        from_camera = findViewById<TextView>(R.id.from_camera)
        from_album = findViewById<TextView>(R.id.from_album)
        upper_layout = findViewById<FrameLayout>(R.id.clickCloseableLayout)
        total_layout= findViewById<LinearLayout>(R.id.totalLayout)
    }

    private fun 设置按钮() {
        cancel_button!!.setOnClickListener { finish() }
        upper_layout!!.setOnClickListener { finish() }
        from_camera!!.setOnClickListener (::拍照选择)
        from_album!!.setOnClickListener (::从图库选择)

        if (ChoosePicture.i().allowFromGallery && (!ChoosePicture.i().allowTakePhoto)){
            total_layout!!.visibility = View.GONE
            从图库选择(null)
        }
        else if ((!ChoosePicture.i().allowFromGallery) && (ChoosePicture.i().allowTakePhoto)){
            total_layout!!.visibility = View.GONE
            拍照选择(null)
        }
    }

    fun 拍照选择(v: View?){

    }

    fun 从图库选择(v: View?){

    }

}