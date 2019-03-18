package com.example.busplaygroundkt

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.busplaygroundkt.data.model.Stops
import com.example.busplaygroundkt.data.remote.RemoteActiveStopsService
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class MainActivity : AppCompatActivity() {
     private val BASEURL = "https://transloc-api-1-2.p.rapidapi.com"
     private val TAG = "MainActivity.kt"
     private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
     private val client = OkHttpClient.Builder()
         .addInterceptor(interceptor)
         .addInterceptor {
             val request = it.request().newBuilder()
                 .header("X-Mashape-Key", "hHcLr1qWHDmshwibREtIrhryL9bcp1Fw9AQjsnCiZyEzRrJKOS")
                 .build()
   it.proceed(request)
         }


         .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val r = Retrofit.Builder()
               .baseUrl(BASEURL)
               .client(client)
               .addConverterFactory(GsonConverterFactory.create())
               .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
               .build()

        val s:RemoteActiveStopsService = r.create(RemoteActiveStopsService::class.java)
            s.getActiveStops(1323,  "40.506831,-74.456645|5000")
                .subscribeOn(Schedulers.io())
                .flatMapIterable { it.data}
                .map { log(it.name) }
                .subscribe()



        /*
            1. Download all stops
            2. Download all active routes
            3. Draw all active routes
            4. Draw all stops
            5. Draw all bus stops
         */
    }

    fun log(str:String?) {
        Log.d(TAG,"  $str")
    }
}
