package com.example.busplaygroundkt.data.remote

import com.example.busplaygroundkt.data.model.Routes
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SegmentsService {
    @GET("segments.json")
    fun getRoutes(
        @Query("agencies")  agencyId:Int, @Query("geo_area") campus:String, @Query("route_id") routeid: Int
    ): Observable<Routes.Response>
}