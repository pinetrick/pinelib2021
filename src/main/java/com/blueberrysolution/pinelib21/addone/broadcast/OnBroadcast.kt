package com.blueberrysolution.pinelib21.addone.broadcast


interface OnBroadcast{
    fun onBroadcast(key: String, withObject: Any?);
}