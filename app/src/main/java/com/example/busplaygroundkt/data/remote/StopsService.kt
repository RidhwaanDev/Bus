package com.example.busplaygroundkt.data.remote

import com.example.busplaygroundkt.data.model.Stops
import com.example.busplaygroundkt.data.model.Vehicles
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface StopsService {
    @GET("stops.json")
    fun getVehicles(
        @Query("agencies")  agencyId:Int, @Query("geo_area") campus:String
    ): Observable<Stops.Response>
}