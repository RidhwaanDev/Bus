package com.example.busplaygroundkt.di

import com.example.busplaygroundkt.data.remote.VehiclesService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RemoteModule {

@Provides @Singleton
fun provideOkHttpClient(): OkHttpClient =
         OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor {
            val request = it.request().newBuilder()
                .header("X-Mashape-Key", "hHcLr1qWHDmshwibREtIrhryL9bcp1Fw9AQjsnCiZyEzRrJKOS")
                .build()
            it.proceed(request)
        }
        .build()

@Provides @Singleton
fun provideRetrofitClient( BASEURL: String,  client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASEURL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

@Provides @Singleton
fun provideVehiclesSergice(r:Retrofit): VehiclesService = r.create(VehiclesService::class.java)



}