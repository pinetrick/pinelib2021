package com.blueberrysolution.pinelib21.addone.broadcast


import com.blueberrysolution.pinelib21.debug.d
import com.blueberrysolution.pinelib21.debug.w
import com.blueberrysolution.pinelib21.debug.i
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.ref.WeakReference

class Broadcast private constructor() {

    var broadcasts = ArrayList<BroadcastRegBean>();
    init{

    }

    fun cleanNull(){
        var y :Int? = 0
        for(i in broadcasts.indices){
            var broadcast = broadcasts[i-y!!];
            if (broadcast.onBroadcast.get() == null) {
                broadcasts.removeAt(i - y);
                y++
            }

        }
    }

    fun reg(key: String, callback: OnBroadcast): Boolean{
        cleanNull();
        for(broadcast in broadcasts){
            if (broadcast.key.equals(key)){
                if (broadcast.onBroadcast.get() != null){
                    if (broadcast.onBroadcast.get() == callback) {
                        w("Cannot reg Broadcast(Already Exist): ${key}")
                        return false;
                    }
                }
            }
        }

        broadcasts.add(BroadcastRegBean(key, WeakReference(callback)));
        i("Reg Broadcast(Total: ${broadcasts.size}): ${key}")
        return true;
    }

    fun send(key: String, withObject: Any? = null){
        i("Sending Broadcast: ${key}")
        cleanNull()
        doAsync {
            uiThread {
                for(broadcast in broadcasts) {
                    if (broadcast.key.equals(key)) {
                        if (broadcast.onBroadcast.get() != null) {
                            d("${key} -> ${broadcast.onBroadcast.get().toString()}")
                            broadcast.onBroadcast.get()!!.onBroadcast(key, withObject);
                        }
                    }
                }
            }
        }
    }

    companion object {
        val i: Broadcast by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED)
        {
            Broadcast()
        }
    }
}