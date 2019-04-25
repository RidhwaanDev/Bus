package com.example.busplaygroundkt.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import com.example.busplaygroundkt.R

class MapUtils (context: Context) {

    val ctx = context

    fun drawableToBitmap( res: Int): Bitmap {

        val bitmap = Bitmap.createBitmap(128,128, Bitmap.Config.ARGB_8888)
        val drawable  = ctx.resources.getDrawable(res)
        val canvas = Canvas(bitmap)

        drawable.setBounds(0,0,bitmap.width,bitmap.height)
        drawable.draw(canvas)

        return bitmap
    }

    fun _draw() : BitmapDrawable {
        val bitmap : Bitmap = drawableToBitmap(R.drawable.ic_bus_temp)
        val paint = Paint().apply { style = Paint.Style.FILL
            color = Color.BLACK
            textSize = 20f
        }

        val height : Float = 0f + bitmap.height / 2
        val canvas = Canvas(bitmap)
        canvas.drawText("hello" ,0f, height , paint)
        return BitmapDrawable(ctx.resources,bitmap)

    }

}