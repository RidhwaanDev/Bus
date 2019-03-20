package com.example.busplaygroundkt.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.busplaygroundkt.R
import com.google.android.gms.maps.*



class MapFragment : Fragment (), OnMapReadyCallback{

    private lateinit var mMap: GoogleMap
    private lateinit var mMapViewModel: MapViewModel
    private val TAG = MapFragment::class.java.simpleName

    companion object {
        fun newInstance() = MapFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel(){
        mMapViewModel = ViewModelProviders.of(this).get(MapViewModel::class.java)
        mMapViewModel.let { lifecycle.addObserver(it) }
        mMapViewModel.loadBusData()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v  = inflater.inflate(R.layout.activity_maps, container ,false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onMapReady(map: GoogleMap?) {
        mMapViewModel.loadBusData()?.observe(this, Observer { item -> item?.forEach{(key,value) ->  Log.d(TAG,"$key => $value")
            /**
             *
             *
             *  Like Transit app there should be a bottom navigation tab with all the active Routes
             *
             *
             *
             *
             */
          }
        })
    }

}