package com.example.busplaygroundkt.data.remote

import com.example.busplaygroundkt.data.model.Segments
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SegmentsService {
    @GET("segments.json")
    fun getSegments(
        @Query("agencies")  agencyId:Int, @Query("geo_area") campus:String, @Query("routes") long_name: String
    ): Observable<Segments.Response>
}