package com.example.busplaygroundkt.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.busplaygroundkt.data.model.LatLng

@Entity(tableName = "ActiveStops")
data class ActiveStopsEntity (
            @PrimaryKey(autoGenerate = true) val id: Long,
            val campus:String,
            val stopName:String,
            val location:LatLng
            )

