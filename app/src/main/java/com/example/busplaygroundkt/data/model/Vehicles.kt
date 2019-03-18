package com.example.busplaygroundkt.data.model

import com.google.gson.annotations.SerializedName

object Vehicles {
    data class Response (@SerializedName("rate_limit") val  rateLimit:Int,
                         @SerializedName("expires_in") val expiresInt:Int,
                         val data:Map<String,List<Vehicle>>
    )

    data class Vehicle(@SerializedName("route_id") val routeId:String,
                       @SerializedName("location") val location:Location
    )
    data class Location(@SerializedName("lat" ) val lat:Double,
                        @SerializedName("lng")  val lng:Double
    )
}

