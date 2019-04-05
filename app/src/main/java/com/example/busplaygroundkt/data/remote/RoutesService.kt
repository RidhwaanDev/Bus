package com.example.busplaygroundkt.data.remote

import com.example.busplaygroundkt.data.model.Routes
import com.example.busplaygroundkt.data.model.Vehicles
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RoutesService {
    @GET("routes.json")
    fun getRoutes(
        @Query("agencies")  agencyId:Int, @Query("geo_area") campus:String
    ): Observable<Routes.Response>
}