package com.blueberrysolution.pinelib21.permission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.blueberrysolution.pinelib21.R
import com.blueberrysolution.pinelib21.basic_class_ext.helper_functions.s
import com.blueberrysolution.pinelib21.context.PineActivity
import com.blueberrysolution.pinelib21.context.a
import com.blueberrysolution.pinelib21.view.message_box.MessageBox
import java.lang.StringBuilder


class PermissionRequireActivity: PineActivity(){
    var permission_list: TextView? = null;
    var granted: RelativeLayout? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.permission_require)

        granted = findViewById(R.id.granted)
        permission_list = findViewById(R.id.features)

        setupPermission();


        granted!!.setOnClickListener(::onGrantBtn);



    }

    private fun setupPermission() {

        var sb = StringBuilder();
        RequirePermission.lastInstance!!.permissionList.map {
            when (it) {
                Manifest.permission.CAMERA -> sb.append(s(R.string.permission_require_camara) + "\r\n")
                Manifest.permission.READ_CONTACTS -> sb.append(s(R.string.permission_require_READ_CONTACTS) + "\r\n")
                Manifest.permission.WRITE_CONTACTS -> sb.append(s(R.string.permission_require_WRITE_CONTACTS) + "\r\n")
                Manifest.permission.READ_EXTERNAL_STORAGE -> sb.append(s(R.string.permission_require_READ_EXTERNAL_STORAGE) + "\r\n")
                Manifest.permission.WRITE_EXTERNAL_STORAGE -> sb.append(s(R.string.permission_require_WRITE_EXTERNAL_STORAGE) + "\r\n")
                Manifest.permission.RECORD_AUDIO -> sb.append(s(R.string.permission_require_MICROPHONE) + "\r\n")
                Manifest.permission.READ_PHONE_STATE -> sb.append(s(R.string.permission_require_READ_PHONE_STATE) + "\r\n")
                Manifest.permission.ACCESS_COARSE_LOCATION -> sb.append(s(R.string.permission_require_ACCESS_COARSE_LOCATION) + "\r\n")
                Manifest.permission.ACCESS_FINE_LOCATION -> sb.append(s(R.string.permission_require_ACCESS_FINE_LOCATION) + "\r\n")

            }
            ""
        }

        permission_list!!.text = sb.toString();
    }


    fun onGrantBtn(view: View?){


        var hasCameraPermission = RequirePermission.lastInstance!!.hasPermissions();

        if (hasCameraPermission) {
            //有调起相机拍照。
            RequirePermission.onGranted!!()
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(
                a(), RequirePermission.lastInstance!!.permissionList,
                RequirePermission.PERMISSION_REQUEST_CODE
            )
        }

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == RequirePermission.PERMISSION_REQUEST_CODE){
            var isTotalGranted = true;
            grantResults.map {
                if (it == PackageManager.PERMISSION_DENIED){
                    isTotalGranted = false;
                }
            }
            if (isTotalGranted) {
                RequirePermission.onGranted!!()
                this.finish();
            } else {
                //拒绝权限，弹出提示框。
                MessageBox().setOnBtnClickListener { idFrom1, input ->
                    if (idFrom1 == 1){
                        RequirePermission.lastInstance!!.onCancel()
                        this.finish();
                    }
                    else{
                        onGrantBtn(null)
                    }
                }.show(s(R.string.photo_choose_no_permission),s(R.string.cancel), s(R.string.retry) )

            }
        }



        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }



}