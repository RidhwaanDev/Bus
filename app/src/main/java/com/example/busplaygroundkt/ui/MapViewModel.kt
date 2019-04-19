package com.example.busplaygroundkt.ui

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.busplaygroundkt.data.model.Routes
import com.example.busplaygroundkt.data.model.Stops
import com.example.busplaygroundkt.data.model.Vehicles
import com.example.busplaygroundkt.data.repository.BusRepository
import com.example.busplaygroundkt.di.BusApplication
import javax.inject.Inject

class MapViewModel : ViewModel(), LifecycleObserver{

    private var liveBusData : LiveData<Map<String, Vehicles.Location>>? = null
    private var liveBusStopData : LiveData<List<Stops.Response>>? = null
    private var liveRouteData: LiveData<List<Routes.Route>>? = null


    @Inject
    lateinit var busRepository: BusRepository

    init {
        initializeDagger()
    }

    fun loadBusData(): LiveData<Map<String,Vehicles.Location>>? {

        liveBusData = null
        liveBusData = MutableLiveData<Map<String,Vehicles.Location>>()
        liveBusData = busRepository.getVehicleLocations()

        return liveBusData
    }

    fun loadBusStops() : LiveData<List<Stops.Response>>?{
        liveBusStopData = null
        liveBusStopData = MutableLiveData<List<Stops.Response>>()
        liveBusStopData = busRepository.getBusStopLocations()
        return liveBusStopData
    }

    fun loadRoutes() : LiveData<List<Routes.Route>>?{
        liveRouteData = null;
        liveRouteData = MutableLiveData<List<Routes.Route>>()
        liveRouteData = busRepository.getRoutes()
        return liveRouteData
    }

    fun loadSegmentsForRoute(long_name: String){
        busRepository.getSegments(long_name)

    }

    private fun initializeDagger() = BusApplication.appComponent.inject(this)



}