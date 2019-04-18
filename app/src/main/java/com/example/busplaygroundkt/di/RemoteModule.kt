package com.example.busplaygroundkt.di

import com.example.busplaygroundkt.Config
import com.example.busplaygroundkt.data.remote.RoutesService
import com.example.busplaygroundkt.data.remote.SegmentsService
import com.example.busplaygroundkt.data.remote.StopsService
import com.example.busplaygroundkt.data.remote.VehiclesService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class RemoteModule {

    @Provides @Singleton @Named("Retrofit")
    fun provideRetrofitClient(@Named("OkHttpClient") client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Config.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Provides @Singleton @Named("OkHttpClient")
    fun makeOkHTTPClient(): OkHttpClient =
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
    fun provideVehiclesService(@Named("Retrofit") r:Retrofit): VehiclesService = r.create(VehiclesService::class.java)

    @Provides @Singleton
    fun provideStopsService(@Named("Retrofit") r:Retrofit): StopsService = r.create(StopsService::class.java)

    @Provides @Singleton
    fun provideRoutesService(@Named("Retrofit") r: Retrofit): RoutesService = r.create(RoutesService::class.java)

    @Provides @Singleton
    fun provideSegmentsService(@Named("Retrofit") r: Retrofit): SegmentsService = r.create(SegmentsService::class.java)




}