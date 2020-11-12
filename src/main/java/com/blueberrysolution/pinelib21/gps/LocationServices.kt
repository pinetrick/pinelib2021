package com.blueberrysolution.pinelib21.gps


import android.annotation.SuppressLint
import android.content.Context
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import com.blueberrysolution.pinelib21.app.c
import java.lang.ref.WeakReference
import android.location.LocationListener as LocationListener

//用法
//LocationServices().startRefresh{}
//这个类请复制到app中使用，常饮用可能导致内存泄漏

class LocationServices: LocationListener {
    var callback: ((Location?) -> Unit)? = null;
    @SuppressLint("MissingPermission")
    fun startRefresh(callback: ((location: Location?) -> Unit)?){
        if (callback != null){
            this.callback = callback;
        }

        var locMan = c().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        var list = locMan.getProviders(true);

        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_FINE //最高精度的


        val provider: String? = locMan.getBestProvider(criteria, true)

        if  ((provider == null) || (provider == "passive")){
            if (callback!= null){
                callback!!(null);
            }
        }


        locMan.requestLocationUpdates( provider, 60000, 100.0f, this);


    }

    override fun onLocationChanged(location: Location?) {
        if (callback!= null){
            callback!!(location);
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onProviderEnabled(provider: String?) {
        startRefresh(null);
    }

    override fun onProviderDisabled(provider: String?) {

    }
}