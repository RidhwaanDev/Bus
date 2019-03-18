package com.example.busplaygroundkt.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.example.busplaygroundkt.data.model.LatLng

@Entity(tableName = "ActiveStops")
data class ActiveStopsEntity (
            @PrimaryKey(autoGenerate = true) val id: Long,
            val campus:String,
            val stopName:String,
            val location:LatLng
            )

