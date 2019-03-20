package com.example.busplaygroundkt.ui

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.busplaygroundkt.data.model.Vehicles
import com.example.busplaygroundkt.data.repository.BusRepository
import com.example.busplaygroundkt.di.BusApplication
import javax.inject.Inject

class MapViewModel : ViewModel(), LifecycleObserver{

    private var liveBusData : LiveData<Map<String, Vehicles.Location>>? = null

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

    private fun initializeDagger() = BusApplication.appComponent.inject(this)



}