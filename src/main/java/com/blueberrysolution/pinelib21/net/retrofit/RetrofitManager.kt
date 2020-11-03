package com.blueberrysolution.pinelib21.net.retrofit


import com.google.gson.GsonBuilder
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import java.lang.Exception
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass


/**
 * 需要在APP中初始化
 * API初始化类
 * RetrofitManager(Config.baseUrl, RetrofitServices::class) //初始化
 *
 */
final class RetrofitManager<T : Any> {

    var hostUrl: String = "";

    var request0: T? = null
    var retrofit: Retrofit? = null


    constructor(hostUrl: String, classInfo: KClass<T>) {
        this.hostUrl = hostUrl;
        mInstance = this;

        try {
            // 初始化okhttp
            if (retrofit == null){
                val client = OkHttpClient.Builder()
                    .connectTimeout(500, TimeUnit.SECONDS)
                    .readTimeout(500, TimeUnit.SECONDS)
                    .writeTimeout(500, TimeUnit.SECONDS)
                    .build()

                // 初始化Retrofit
                retrofit = Retrofit.Builder()
                    .client(client)
                    .baseUrl(hostUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                    .build()

                request0 = retrofit!!.create(classInfo.java)
            }

        } catch (e: Exception) {

        }


    }


    fun req(): T {
        return request0!!
    }


    companion object {

        private var mInstance: RetrofitManager<*>? = null

        fun i(): RetrofitManager<*> {
            if (mInstance == null) {
                throw Exception("Please Run RetrofitManager(??, ??) on APP");
            }
            return mInstance!!

        }


    }
}