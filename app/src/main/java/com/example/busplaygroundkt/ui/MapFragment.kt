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
import com.example.busplaygroundkt.Config
import com.example.busplaygroundkt.P
import com.example.busplaygroundkt.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.*


class MapFragment : Fragment (), OnMapReadyCallback{

    private lateinit var mMapViewModel: MapViewModel
    private lateinit var mMapView: MapView

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
        val markerList  = arrayListOf<Marker?>()

        mMap = map
        mMap?.moveCamera(CameraUpdateFactory.newLatLng(nb))
        mMapViewModel.loadBusData()?.observe(this, Observer { vehicles ->
            // clear
            for(i in 0 until markerList.size){
                markerList[i]?.re.mmove()
            }
            // draw
            vehicles?.forEach { (routeid,bus) ->
               run{
                    // remove current markers ( we dont want to redraw the old markers )

                   val single_marker: Marker? = mMap?.addMarker(MarkerOptions().position(LatLng(bus.lat, bus.lng)).title(routeid))
                   single_marker?.showInfoWindow()
                   markerList.add(single_marker)
                   }


            }
        })

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