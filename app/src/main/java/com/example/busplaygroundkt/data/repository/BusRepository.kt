package com.example.busplaygroundkt.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import com.example.busplaygroundkt.Config
import com.example.busplaygroundkt.P
import com.example.busplaygroundkt.data.model.Routes
import com.example.busplaygroundkt.data.model.Segments
import com.example.busplaygroundkt.data.model.Stops
import com.example.busplaygroundkt.data.model.Vehicles
import com.example.busplaygroundkt.data.remote.RoutesService
import com.example.busplaygroundkt.data.remote.SegmentsService
import com.example.busplaygroundkt.data.remote.StopsService
import com.example.busplaygroundkt.data.remote.VehiclesService
import com.example.busplaygroundkt.di.AppComponent
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.fragment_map.view.*
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
class BusRepository @Inject constructor( private val busService: VehiclesService,
                                         private val busStopsService: StopsService,
                                         private val routesService: RoutesService,
                                         private val segmentsService: SegmentsService

                                        ) {

    fun getVehicleLocations(): LiveData<Map<String, Vehicles.Location>> {
        val mutLiveData = MutableLiveData<Map<String, Vehicles.Location>>()
        val locationMap = mutableMapOf<String, Vehicles.Location>()

        Observable.interval(0, 5, TimeUnit.SECONDS)
            .flatMap { busService.getVehicles(Config.agencyID, Config.nbCampus) }
            .subscribeOn(Schedulers.io())
            .map { it.data.getValue(Config.agencyID.toString()) }
            .subscribe({ item ->
                item.forEach { locationMap.put(it.vehicleId, it.location) }
                mutLiveData.postValue(locationMap)
            }
                , { t: Throwable? -> t?.printStackTrace() })

        return mutLiveData
    }

    fun zipBus(): Observable<Vehicles.Response> {

        val busObservable: Observable<Vehicles.Response> = busService.getVehicles(Config.agencyID, Config.nbCampus)
        val routesObservable: Observable<Routes.Response> = routesService.getRoutes(Config.agencyID, Config.nbCampus)

        val routeid_to_bus_name = mutableMapOf<String, String>()
        val result: Observable<Vehicles.Response> = Observable.zip(
            busObservable,
            routesObservable,
            object : BiFunction<Vehicles.Response, Routes.Response, Vehicles.Response> {

                override fun apply(t1: Vehicles.Response, t2: Routes.Response): Vehicles.Response {

                    t2.data.getValue(Config.agencyID.toString())
                        .forEach { route -> routeid_to_bus_name.put(route.routeId, route.long_name) }

                    for (vehicle in t1.data.getValue(Config.agencyID.toString())) {
                        vehicle.busName = routeid_to_bus_name[vehicle.routeId]!!
                    }

                    return t1
                }
            })

        return result
    }

    fun getProperBus(): LiveData<List<Vehicles.Vehicle>> {

        val mutLiveData = MutableLiveData<List<Vehicles.Vehicle>>()
        val listOfBus = mutableListOf<Vehicles.Vehicle>()

        Observable.interval(0, 5, TimeUnit.SECONDS)
            .subscribe {
                zipBus().subscribeOn(Schedulers.io()).subscribe { result ->

                    result.data.getValue(Config.agencyID.toString()).forEach { item ->
                        listOfBus.add(item)

                    }

                    mutLiveData.postValue(listOfBus)
                }
            }


        return mutLiveData
    }

    fun getBusStopLocations(): LiveData<List<Stops.Response>>? {

        val listOfStops = mutableListOf<Stops.Response>()
        val mutLiveData = MutableLiveData<List<Stops.Response>>()

        busStopsService.getVehicles(Config.agencyID, Config.nbCampus)
            .subscribeOn(Schedulers.io())
            .subscribe { result ->
                listOfStops.add(result)
                mutLiveData.postValue(listOfStops)

            }

        return mutLiveData
    }

    fun getRoutes(): LiveData<List<Routes.Route>>? {
        val mutLiveData = MutableLiveData<List<Routes.Route>>()

        routesService.getRoutes(Config.agencyID, Config.nbCampus)
            .subscribeOn(Schedulers.io())
            .map { it.data.getValue(Config.agencyID.toString()) }
            .subscribe({
                mutLiveData.postValue(it)
            },
                { t: Throwable? -> t?.printStackTrace() })

        return mutLiveData
    }




    fun getSegments(long_name: String ) : LiveData<Segments.Response>? {
        val mutLiveData = MutableLiveData<Segments.Response>()

        routesService.getRoutes(Config.agencyID,Config.nbCampus)
            .subscribeOn(Schedulers.io())
            .map{it.data.getValue(Config.agencyID.toString())}
            .subscribe { routes_list ->
                Observable.just(routes_list)
                    .flatMapIterable { it }
                    .filter{it.long_name == long_name}
                    .subscribe { result ->
                      segmentsService.getSegments(Config.agencyID,Config.nbCampus,result.routeId)
                          .subscribeOn(Schedulers.io())
                          .subscribe ({ result_f ->
                              result_f._routeid = result.routeId
                              mutLiveData.postValue(result_f)

                          }, {t: Throwable? -> t?.printStackTrace()})

                    }

            }
        return mutLiveData
    }
}