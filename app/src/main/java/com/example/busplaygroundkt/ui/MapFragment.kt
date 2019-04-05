package com.example.busplaygroundkt.ui

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Bitmap
import android.graphics.Canvas
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
import com.google.android.gms.maps.model.PolylineOptions
import android.graphics.Color
import com.example.busplaygroundkt.P
import com.example.busplaygroundkt.ui.components.BusDrawable
import kotlinx.coroutines.*
import android.support.v4.os.HandlerCompat.postDelayed
import android.os.SystemClock
import android.view.animation.LinearInterpolator
import com.google.android.gms.maps.Projection
import com.google.android.gms.maps.model.Marker
import android.os.Handler


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
        val v  = inflater.inflate(com.example.busplaygroundkt.R.layout.fragment_map, container ,false)
        mMapView = v.findViewById(com.example.busplaygroundkt.R.id.map)
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
        val listOfCoordsForLine = mutableListOf<LatLng>()
        mMap = map
        mMap?.setOnMarkerClickListener(this)
        mMap?.moveCamera(CameraUpdateFactory.newLatLng(nb))

        val routeid_id_2_location = mutableMapOf<String, LatLng>()

//        mMapViewModel.loadBusStops()?.observe(this, Observer { result ->
//            result?.forEach { response ->
//                Observable.just(response.data)
//                    .flatMapIterable { it }
//                    .subscribe { item ->
//                        if(routeid_id_2_location.get(item.stopID) == null){
//                            routeid_id_2_location.put(item.stopID, LatLng(item.location.lat,item.location.lng))
//                        }
//
//                    }
//            }
//
//        })
        val colors = listOf(Color.BLACK,Color.BLUE,Color.RED,Color.MAGENTA)

        val route = "Route EE"
        val options = PolylineOptions()
//        mMapViewModel.loadRoutes()?.observe(this, Observer { routes ->
//           routes
//               ?.filter { it.is_active }
//               ?.filter { it.long_name.equals(route)}
//               ?.forEach { route ->
//                   Observable.just(route.stops)
//                       .flatMapIterable { it }
//                       .buffer(2)
//                       .subscribe ({ item ->
//                                options.add(routeid_id_2_location.get(item.get(0)))
//                                    .add(routeid_id_2_location.get(item.get(1)))
//                                    .color(colors[3])
//                                    .width(9f)
//
//                                    }
//
//                           , {t: Throwable ->  t.printStackTrace()})
//
//               }
//
//            mMap?.addPolyline(options)
//
//        })

        load_bus_into_ui(map)
        load_stop_into_ui(map)
        load_route_into_ui(map)

            mMapViewModel.loadBusData()?.observe(this, Observer { vehicles ->
            vehicles?.forEach { (routeid,bus) ->
                if(markerList[routeid] != null){
                    var marker = markerList[routeid]

                    if(marker?.position != LatLng(bus.lat,bus.lng)) {
                        changePositionSmoothly(marker,LatLng(bus.lat,bus.lng))
                        marker?.position = LatLng(bus.lat,bus.lng)

                    }



                } else {

                        val bitmap = Bitmap.createBitmap(32,32,Bitmap.Config.ARGB_8888)
                        val drawable  = resources.getDrawable(R.drawable.ic_bus_temp)
                        val canvas = Canvas(bitmap)
                        drawable.setBounds(0,0,bitmap.width,bitmap.height)
                        drawable.draw(canvas)

                        val marker= mMap?.addMarker(MarkerOptions().position(LatLng(bus.lat, bus.lng)).title(routeid))

                        marker?.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap))
                        markerList.put(routeid,marker)
                    }
            }
        })
    }


    fun changePositionSmoothly(marker:Marker?, newLatLng: LatLng) : Marker?  {
        if(marker == null){
            return null
        }

        val animation = ValueAnimator.ofFloat(0f, 100f)
        var previousStep = 0f
        val deltaLatitude = newLatLng.latitude - marker.position.latitude
        val deltaLongitude = newLatLng.longitude - marker.position.longitude

        animation.setDuration(1500)

        animation.addUpdateListener { updatedAnimation ->
            val deltaStep = updatedAnimation.getAnimatedValue() as Float - previousStep
            previousStep = updatedAnimation.getAnimatedValue() as Float
            marker.position = LatLng(marker.position.latitude + deltaLatitude * deltaStep * 1/100, marker.position.longitude + deltaStep * deltaLongitude * 1/100)
        }

        animation.start()
        return marker
    }

    fun load_bus_into_ui(map: GoogleMap?){
        val markerList = mutableMapOf<String, Marker?>()

    }
    fun load_route_into_ui(map: GoogleMap?){

    }
    fun load_stop_into_ui(map: GoogleMap?){

    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        return false
    }

    private fun getDrawable(): BitmapDescriptor = runBlocking {
         loadDrawable("BUS")
    }

    private suspend fun loadDrawable(busText: String): BitmapDescriptor {
        val deferred_drawable : Deferred<BusDrawable> = GlobalScope.async {
            delay(1000L)
            BusDrawable(busText)
        }
        val final_drawable = deferred_drawable.await()
        val final_icon: BitmapDescriptor = final_drawable.getMarkerIconFromDrawable()
        return final_icon

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