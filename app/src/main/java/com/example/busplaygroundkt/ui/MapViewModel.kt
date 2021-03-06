package com.example.busplaygroundkt.ui

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.busplaygroundkt.data.model.Routes
import com.example.busplaygroundkt.data.model.Segments
import com.example.busplaygroundkt.data.model.Stops
import com.example.busplaygroundkt.data.model.Vehicles
import com.example.busplaygroundkt.data.repository.BusRepository
import com.example.busplaygroundkt.di.BusApplication
import javax.inject.Inject

class MapViewModel : ViewModel(), LifecycleObserver{

    private var liveBusData : LiveData<Map<String, Vehicles.Location>>? = null
    private var liveBusStopData : LiveData<List<Stops.Response>>? = null
    private var liveRouteData: LiveData<List<Routes.Route>>? = null
    private var liveSegmentData: LiveData<Segments.Response>? = null



    @Inject
    lateinit var busRepository: BusRepository

    init {
        initializeDagger()
    }

    fun loadBusData(routeid : String?): LiveData<Map<String,Vehicles.Location>>? {

        liveBusData = null
        liveBusData = MutableLiveData<Map<String,Vehicles.Location>>()
        liveBusData = busRepository.getVehicleLocations(routeid)

        return liveBusData
    }

    fun loadBusStops() : LiveData<List<Stops.Response>>?{
        liveBusStopData = null
        liveBusStopData = MutableLiveData<List<Stops.Response>>()
        liveBusStopData = busRepository.getBusStopLocations()
        return liveBusStopData
    }

    fun loadRoutes() : LiveData<List<Routes.Route>>?{
        liveRouteData = null
        liveRouteData = MutableLiveData<List<Routes.Route>>()
        liveRouteData = busRepository.getRoutes()
        return liveRouteData
    }

    fun loadSegmentsForRoute(long_name: String): LiveData <Segments.Response>? {
        liveSegmentData = null
        liveSegmentData = MutableLiveData<Segments.Response>()
        liveSegmentData = busRepository.getSegments(long_name)
        return liveSegmentData


    }

    private fun initializeDagger() = BusApplication.appComponent.inject(this)



}