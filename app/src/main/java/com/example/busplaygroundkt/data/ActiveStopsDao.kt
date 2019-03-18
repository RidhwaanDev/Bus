package com.example.busplaygroundkt.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.example.busplaygroundkt.data.model.Stops

/*
data access object (dao) is an object that defines the methods to interact with out databse ( inserting,deleteing,querying etc.)
 */

@Dao
interface ActiveStopsDao {

    @Query("SELECT * FROM ActiveStops")
    fun getAllActiveStops(): LiveData<List<Stops>>
}