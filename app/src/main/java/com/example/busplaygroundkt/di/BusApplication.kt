package com.example.busplaygroundkt.di

import android.app.Application

class BusApplication : Application()  {

    companion object {
        lateinit var appComponent: AppComponent

    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger(){
        appComponent = DaggerAppComponent
            .builder()
            .remoteModule(RemoteModule())
            .build()
    }


}