package com.blueberrysolution.pinelib21.gps

import android.content.Intent
import android.net.Uri
import com.blueberrysolution.pinelib21.R
import com.blueberrysolution.pinelib21.basic_class_ext.helper_functions.s
import com.blueberrysolution.pinelib21.context.a

fun Float.distanceToString(): String{
    if (this < 1000){
        return this.toInt().toString() + s(R.string.unit_m);
    }
    else{
        return String.format("%.1f", (this/1000)).toString() + s(R.string.unit_km);
    }
}

fun callNavigation(lat: Double, lng: Double){
    val gmmIntentUri = Uri.parse("google.navigation:q=" + lat.toString() + "," + lng.toString())
    // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    // Make the Intent explicit by setting the Google Maps package
    mapIntent.setPackage("com.google.android.apps.maps")

    // Attempt to start an activity that can handle the Intent
    a().startActivity(mapIntent)
}



