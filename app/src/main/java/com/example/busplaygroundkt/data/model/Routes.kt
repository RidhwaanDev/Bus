package com.example.busplaygroundkt.data.model

import com.google.gson.annotations.SerializedName

object Routes {
    data class Response (@SerializedName("rate_limit") val  rateLimit:Int,
                         @SerializedName("expires_in") val expiresInt:Int,
                         val data:Map<String,List<Route>>
    )

    data class Route(  @SerializedName("route_id")  val routeId:String,
                       @SerializedName("color")     val color:String,
                       @SerializedName("is_active") val is_active: Boolean,
                       @SerializedName("long_name") val long_name:String,
                        @SerializedName("stops")    val stops: List<String>
    )
}

