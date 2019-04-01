package com.example.busplaygroundkt.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import com.example.busplaygroundkt.Config
import com.example.busplaygroundkt.P
import com.example.busplaygroundkt.data.model.Routes
import com.example.busplaygroundkt.data.model.Stops
import com.example.busplaygroundkt.data.model.Vehicles
import com.example.busplaygroundkt.data.remote.RoutesService
import com.example.busplaygroundkt.data.remote.StopsService
import com.example.busplaygroundkt.data.remote.VehiclesService
import com.example.busplaygroundkt.di.AppComponent
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

/**
 * Single source truth of all Data
 */

@Singleton
class BusRepository @Inject constructor( private val busService: VehiclesService, private val busStopsService: StopsService, private val routesService: RoutesService) {


    fun getVehicleLocations() : LiveData<Map<String,Vehicles.Location>> {
         val mutLiveData = MutableLiveData<Map<String,Vehicles.Location>>()
         val locationMap =  mutableMapOf<String,Vehicles.Location>()

        Observable.interval(0,5, TimeUnit.SECONDS)
                .flatMap { busService.getVehicles(Config.agencyID, Config.nbCampus) }
                .subscribeOn(Schedulers.io())
                .map { it.data.getValue(Config.agencyID.toString())}
                .subscribe({item -> item.forEach { locationMap.put(it.vehicleId,it.location) }
                    mutLiveData.postValue(locationMap)
                }
                , {t: Throwable? -> t?.printStackTrace()})

            return mutLiveData
        }

    fun getBusStopLocations() : LiveData<List<Stops.Response>>? {
        val listOfStops = mutableListOf<Stops.Response>()
        val mutLiveData = MutableLiveData<List<Stops.Response>>()

        busStopsService.getVehicles(Config.agencyID,Config.nbCampus)
            .subscribeOn(Schedulers.io())
            .subscribe{result ->
                listOfStops.add(result)
                mutLiveData.postValue(listOfStops)

            }


        return mutLiveData

    }

    fun getRoutes() : LiveData<List<Routes.Route>>? {
        val mutLiveData = MutableLiveData<List<Routes.Route>>()

        routesService.getRoutes(Config.agencyID, Config.nbCampus)
            .subscribeOn(Schedulers.io())
            .map { it.data.getValue(Config.agencyID.toString()) }
            .subscribe({
                mutLiveData.postValue(it)
            },
                {t: Throwable? -> t?.printStackTrace()})

        return mutLiveData


    }


}