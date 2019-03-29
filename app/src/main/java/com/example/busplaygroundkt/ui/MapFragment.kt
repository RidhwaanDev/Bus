package com.example.busplaygroundkt.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.busplaygroundkt.Config
import com.example.busplaygroundkt.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import io.reactivex.Observable


class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private lateinit var mMapViewModel: MapViewModel
    private lateinit var mMapView: MapView
    private val markerList = mutableMapOf<String, Marker?>()


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
        val listOfCoordsForLine = mutableListOf<LatLng>()
        mMap = map
        mMap?.setOnMarkerClickListener(this)
        mMap?.moveCamera(CameraUpdateFactory.newLatLng(nb))

//        mMapViewModel.loadBusData()?.observe(this, Observer { vehicles ->
//            vehicles?.forEach { (routeid,bus) ->
//                    if(markerList[routeid] != null){
////                        println("updating bus position")
//                        val marker = markerList[routeid]
//                        marker?.position = LatLng(bus.lat, bus.lng)
//                    } else {
////                        println("creating bus cuz it didnt exists before")
//
//                        val marker= mMap?.addMarker(MarkerOptions().position(LatLng(bus.lat, bus.lng)).title(routeid))
//                        markerList.put(routeid,marker)
//                    }
//            }
//
//        })
        val options = PolylineOptions()
        mMapViewModel.loadBusStops()?.observe(this, Observer { result ->
            result?.forEach {

                    response ->
                response.data.forEach { stop ->

                         println("${stop.location.lat},${stop.location.lng}")

                         options.add(LatLng(stop.location.lat,stop.location.lat))


                         if(markerList[stop.stopID] != null){
                         val marker = markerList[stop.stopID]
                         marker?.position = LatLng(stop.location.lat, stop.location.lng)
                    } else {

                        val marker= mMap?.addMarker(MarkerOptions().position(LatLng(stop.location.lat, stop.location.lng)).title(stop.name))
                        markerList.put(stop.stopID,marker)
                    }

                }
            }
            mMap?.addPolyline(options)
        })
    }


    override fun onMarkerClick(p0: Marker?): Boolean {
        return false
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