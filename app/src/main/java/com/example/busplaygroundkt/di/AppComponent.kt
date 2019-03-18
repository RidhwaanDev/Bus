package com.example.busplaygroundkt.di

import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(RemoteModule::class))
@Singleton interface AppComponent {
    fun inject()
}
