package com.example.busplaygroundkt.data.remote

import com.example.busplaygroundkt.data.model.Stops
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
interface RemoteActiveStopsService {
    @GET("stops.json")
    fun getActiveStops(
        @Query("agencies")  agencyId:Int, @Query("geo_area") campus:String
    ): Observable<Stops.Response>
}