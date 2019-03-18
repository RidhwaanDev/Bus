package com.example.busplaygroundkt

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.busplaygroundkt.data.model.Stops
import com.example.busplaygroundkt.data.model.Vehicles
import com.example.busplaygroundkt.data.remote.RemoteActiveStopsService
import com.example.busplaygroundkt.data.remote.VehiclesService
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val e:VehiclesService = r.create(VehiclesService::class.java)
        e.getVehicles(1323,"40.506831,-74.456645|5000")
            .subscribeOn(Schedulers.io())
            .map { it.data.get("1323")!!}
            .flatMapIterable { it}
            .map { log(it.location.toString())}
            .subscribe()
    }

    fun log(str:String?) {
        Log.d(TAG,"  $str")
    }
}
