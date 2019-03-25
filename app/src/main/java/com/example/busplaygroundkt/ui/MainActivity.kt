package com.example.busplaygroundkt.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.busplaygroundkt.R

/**
 *
 *
 * ENTRY POINT TO THE APPLICATION
 *
 */

class MainActivity : AppCompatActivity() {
     private val TAG = "MainActivity.kt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = MapFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.content,mapFragment)
            .commit()



    }

    fun log(str:String?) {
        Log.d(TAG,"  $str")
    }

}
