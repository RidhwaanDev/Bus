package com.example.busplaygroundkt.data.model

import com.google.gson.annotations.SerializedName
import java.lang.NullPointerException

object Stops {
    data class Response (@SerializedName("rate_limit") val  rateLimit:Int,
                         @SerializedName("expires_in") val expiresInt:Int,
                         val data:List<Stop>
    )

    data class Stop(@SerializedName("stop_id") val stopID:String,
                    val name:String

    )
}



