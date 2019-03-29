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
import android.widget.Toast
import com.example.busplaygroundkt.Config
import com.example.busplaygroundkt.P
import com.example.busplaygroundkt.R
import com.example.busplaygroundkt.data.model.Vehicles
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.*
import java.lang.NullPointerException


class MapFragment : Fragment (), OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private lateinit var mMapViewModel: MapViewModel
    private lateinit var mMapView: MapView
    private val markerList = mutableMapOf<String,Marker?>()


    private var mMap :GoogleMap? = null
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
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v  = inflater.inflate(R.layout.fragment_map, container ,false)
        mMapView = v.findViewById(R.id.map)
        mMapView.onCreate(savedInstanceState)
        mMapView.getMapAsync(this)


        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMapView.getMapAsync(this)

    }

    /**
     *
     *  GET IT DRAW ALL BUSSES IN REAL TIME ( FOR THURSDAY )
     *
     */


    override fun onMapReady(map: GoogleMap?) {
        val nb = LatLng(Config.NJ_LAT, Config.NJ_LNG)
        val markerList = mutableMapOf<String, Marker?>()


        mMap = map
        mMap?.setOnMarkerClickListener(this)
        mMap?.moveCamera(CameraUpdateFactory.newLatLng(nb))

        mMapViewModel.loadBusData()?.observe(this, Observer { vehicles ->
            vehicles?.forEach { (routeid,bus) ->
                    if(markerList[routeid] != null){

                        val marker = markerList[routeid]
                        marker?.position = LatLng(bus.lat, bus.lng)
                    } else {
                        val marker= mMap?.addMarker(MarkerOptions().position(LatLng(bus.lat, bus.lng)).title(routeid))
                        markerList.put(routeid,marker)

                    }


            }

        }

        )
//        for((k,v) in markerList) {
//            val s = mMap?.addMarker(MarkerOptions().position(LatLng(v.lat, v.lng)).snippet(k))
//            s?.tag = "$k is here"
//            s?.showInfoWindow()
//        }
//        markerList.clear()


    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        P.s("marker clicked", this)

        return false
    }

    fun makePolyline(coords: List<LatLng>) : PolylineOptions {
        val options = PolylineOptions()
        coords.forEach { latlng -> options.add(latlng) }
        return options
    }


    override fun onDestroy() {
        mMapView.onDestroy()
        super.onDestroy()
    }

    override fun onStart() {
        mMapView.onStart()
        super.onStart()
    }

    override fun onResume() {
        mMapView.onResume()
        super.onResume()
    }

    override fun onStop() {
        mMapView.onStop()
        super.onStop()
    }

    override fun onLowMemory() {
        mMapView.onLowMemory()
        super.onLowMemory()
    }



}