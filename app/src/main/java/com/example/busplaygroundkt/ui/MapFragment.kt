package com.example.busplaygroundkt.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
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
import com.example.busplaygroundkt.ui.components.BusDrawable
import kotlinx.coroutines.*
import android.os.SystemClock
import com.google.android.gms.maps.model.Marker
import android.os.Handler
import android.view.animation.AccelerateDecelerateInterpolator
import com.example.busplaygroundkt.data.repository.BusRepository
import com.google.maps.android.PolyUtil
import java.lang.NullPointerException
import java.lang.Runnable


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

    override fun onMapReady(map: GoogleMap?) {

        val nb = LatLng(Config.NJ_LAT, Config.NJ_LNG)
        val listOfCoordsForLine = mutableListOf<LatLng>()
        val route_f = "4012626"

        mMap = map
        mMap?.setOnMarkerClickListener(this)
        mMap?.moveCamera(CameraUpdateFactory.newLatLng(nb))
        val routeline  = PolylineOptions().color(Color.RED).width(5f)
        mMapViewModel.loadSegmentsForRoute(route_f)?.observe(this, Observer {
            it?.data?.forEach { key, value ->
                println(value)
                val bounds = LatLngBounds.Builder()
                mMap?.addPolyline(routeline.addAll(PolyUtil.decode(value)))

                // PolyUtil.decode(value).forEach { bounds.include(it) }
            }

        })


        // change to to use biewmodel
//        mMapViewModel.busRepository.getProperBus().observe(this, Observer { result -> result?.forEach { item ->
//            val routeid = item.routeId
//            val buspos = LatLng(item.location.lat , item.location.lng)
//            val busname = item.busName.removePrefix("Route ")
//
//            println(routeid)
//            for((key,value) in markerList){
//                if (routeid == key)
//                    println("its equals")
//                else {
//                    println("no bueno")
//                }
//
//                if(key == routeid){
//                    value?.setIcon(BitmapDescriptorFactory.fromBitmap(_draw(busname).bitmap))
//                }
//            }
//
//        } })
//
//
//
//        val routeid_id_2_location = mutableMapOf<String, LatLng>()
//
//        mMapViewModel.loadBusStops()?.observe(this, Observer { result ->
//            result?.forEach { response ->
//                Observable.just(response.data)
//                    .flatMapIterable { it }
//                    .subscribe { item ->
//                        if(routeid_id_2_location.get(item.stopID) == null){
//                            routeid_id_2_location.put(item.stopID, LatLng(item.location.lat,item.location.lng))
//                        }
//
//                        val marker = mMap?.addMarker(MarkerOptions().title(item.name).position(LatLng(item.location.lat, item.location.lng)))
//                        marker?.setIcon(BitmapDescriptorFactory.fromBitmap(drawableToBitmap(R.drawable.ic_stop_temp)))
//                    }
//            }
//        })
//
//
//        val colors = listOf(Color.BLACK,Color.BLUE,Color.RED,Color.MAGENTA)
//        val route = "Route EE"
//        val options = PolylineOptions().color(Color.BLACK).width(9f)
//
//        mMapViewModel.loadRoutes()?.observe(this, Observer { routes ->
//            routes
//                ?.filter { it.is_active && it.long_name.equals(route)}
//                ?.forEach { route ->
//                    Observable.just(route.stops)
////                       .flatMapIterable { it }
//                        .subscribe ({ item ->
//                                item.forEach { id ->
//
//                                    routeid_id_2_location[id]
//                                    val marker = mMap?.addMarker(MarkerOptions().position(routeid_id_2_location[id]!!))
//                                    options.add(routeid_id_2_location[id])
//
//
//                                }
//
//                        }
//
//                            , {t: Throwable ->  t.printStackTrace()})
//                }
//
//            mMap?.addPolyline(options)
//
//        })


//        mMapViewModel.loadBusData()?.observe(this, Observer { vehicles ->
//            vehicles?.forEach { (routeid,bus) ->
//                if(markerList[routeid] != null){
//
//                    var marker = markerList[routeid]
//                    val newpos = LatLng(bus.lat,bus.lng)
//
//                    //  change pos smoothly
//                    val handler = Handler()
//                    handler.post(map_animate(marker,marker?.position,newpos,handler))
//
//                } else {
//
//                    val marker= mMap?.addMarker(MarkerOptions().position(LatLng(bus.lat, bus.lng)).title(routeid))
//                    marker?.setIcon(BitmapDescriptorFactory.fromBitmap(_draw("").bitmap))
//                    markerList.put(routeid,marker)
//                }
//            }
//        })
    }

    fun drawableToBitmap( res: Int): Bitmap {

        val bitmap = Bitmap.createBitmap(32,32,Bitmap.Config.ARGB_8888)
        val drawable  = resources.getDrawable(res)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0,0,bitmap.width,bitmap.height)
        drawable.draw(canvas)

        return bitmap
    }

    fun _draw( busName : String) : BitmapDrawable{
        val bitmap : Bitmap = drawableToBitmap(R.drawable.ic_bus_temp)
        val paint = Paint().apply { style = Paint.Style.FILL
            color = Color.BLACK
            textSize = 12f
            textAlign = Paint.Align.CENTER
            typeface = Typeface.DEFAULT_BOLD
        }

        val width : Float = 0f + bitmap.width / 2
        val height : Float = 0f + bitmap.height / 2

        val canvas = Canvas(bitmap)
        canvas.drawText(busName , width , height , paint)
        return BitmapDrawable(context.resources,bitmap)

    }
    /**
     *  I have no idea how this works
     */
    inner class map_animate(val marker: Marker?, val oldpos : LatLng?, val newpos: LatLng, val handler: Handler) : Runnable {
        val start : Long = SystemClock.uptimeMillis()
        val interpolator = AccelerateDecelerateInterpolator()
        val duration : Float = 3000f

        var elapsed: Long = 0
        var t : Float = 0f
        var v : Float = 0f
        var hidemarker : Boolean = false

        override fun run() {
            elapsed = SystemClock.uptimeMillis() - start
            t = elapsed / duration
            v = interpolator.getInterpolation(t)
            var curr = LatLng(((oldpos!!.latitude) * ( 1 - t) + newpos.latitude * t) , oldpos!!.longitude* (1 -t) + newpos.longitude* t)

            marker?.position = curr

            if(t < 1){
                handler.postDelayed(this,16)
            } else {
                if(hidemarker) marker?.setVisible(false) else marker?.setVisible(true)
            }
        }
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