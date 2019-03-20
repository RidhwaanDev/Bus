package com.example.busplaygroundkt.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import com.example.busplaygroundkt.Config
import com.example.busplaygroundkt.data.model.Vehicles
import com.example.busplaygroundkt.data.remote.VehiclesService
import com.example.busplaygroundkt.di.AppComponent
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton
/**
 * Single source truth of all Data
 */

@Singleton
class BusRepository @Inject constructor( private val busService: VehiclesService) {


    fun getVehicleLocations() : LiveData<Map<String,Vehicles.Location>> {
         val mutLiveData = MutableLiveData<Map<String,Vehicles.Location>>()
         val locationMap =  mutableMapOf<String,Vehicles.Location>()

        busService.getVehicles(Config.agencyID, Config.nbCampus)
             .subscribeOn(Schedulers.io())
             .map {it.data.get(Config.nbCampus)!!}
             .subscribe({item ->
                    item.forEach{
                        locationMap.put(it.routeId, it.location)
                    }
                    mutLiveData.value = locationMap
            }, {t:Throwable? -> t?.printStackTrace()}

            )

        return mutLiveData

        }

}