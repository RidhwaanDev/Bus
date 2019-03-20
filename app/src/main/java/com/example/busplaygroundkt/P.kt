package com.example.busplaygroundkt

import android.util.Log

/*
 Util class called Pepsi
 */
object P {

     fun s( str:String?, obj: Any ){
        Log.d(obj.javaClass.simpleName, "$str   \n" )
    }

     fun s(obj: Any?, context: Any){
        Log.d(context.javaClass.simpleName, obj.toString())
    }

}