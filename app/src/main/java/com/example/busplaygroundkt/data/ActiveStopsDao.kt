package com.example.busplaygroundkt.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.busplaygroundkt.data.model.Stops

/*
data access object (dao) is an object that defines the methods to interact with out databse ( inserting,deleteing,querying etc.)
 */

@Dao
interface ActiveStopsDao {

    @Query("SELECT * FROM ActiveStops")
    fun getAllActiveStops(): LiveData<List<Stops>>
}