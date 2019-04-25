package com.example.busplaygroundkt.data.model

import android.app.DownloadManager
import com.google.gson.annotations.SerializedName

object Segments {
    data class Response (@SerializedName("rate_limit")  val  rateLimit:Int,
                         @SerializedName("expires_in")  val expiresInt:Int,
                                                                val data: Map<String,String>,
                                var _routeid : String


    )

}