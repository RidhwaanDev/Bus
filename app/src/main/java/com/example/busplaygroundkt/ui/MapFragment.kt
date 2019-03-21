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
import com.example.busplaygroundkt.P
import com.example.busplaygroundkt.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*


class MapFragment : Fragment (), OnMapReadyCallback{

    private lateinit var mMapViewModel: MapViewModel
    private val TAG = MapFragment::class.java.simpleName

    companion object {
        fun newInstance() = MapFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(map == null) P.s("MAP IS NULL", this)

        initViewModel()


    }

    private fun initViewModel(){
        mMapViewModel = ViewModelProviders.of(this).get(MapViewModel::class.java)
        mMapViewModel.let { lifecycle.addObserver(it) }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v  = inflater.inflate(R.layout.activity_maps, container ,false)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onMapReady(map: GoogleMap?) {

        val latlng = LatLng(40.4862, 74.4518)
        map?.moveCamera(CameraUpdateFactory.newLatLng(latlng))
        P.s("OnMapReady",this)
        mMapViewModel.loadBusData()?.observe(this, Observer { result ->
            P.s("IM HERE", this)
            result?.forEach { k, v ->
                P.s("$k   $v", "map")

                map?.addMarker(MarkerOptions().position(LatLng(v.lat,v.lng)))

            }
        })





    }

}