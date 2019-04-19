package com.example.busplaygroundkt.ui.components

import android.graphics.*
import android.graphics.drawable.Drawable
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class BusDrawable(val busId: String) : Drawable() {

    private val redPaint:Paint = Paint().apply { setARGB(255,255,0,0) }
    private val someOtherPaint:Paint = Paint().apply { setARGB(255,9,220,0) }


    // convert a drawable to a BitMapDescriptor for google maps marker


    override fun draw(canvas: Canvas) {
        val width: Int = bounds.width()
        val height: Int = bounds.height()
        val radius: Float = Math.min(width, height).toFloat() / 2f
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, redPaint)
        canvas.drawText(busId, (width / 2).toFloat(), (height / 2).toFloat(), redPaint)
    }

    fun getMarkerIconFromDrawable() : BitmapDescriptor {
        val canvas = Canvas()                                                                    // each pixel is 32 bit int , 4 bytes per bixel
        val bitmap: Bitmap = Bitmap.createBitmap(this.intrinsicWidth,this.intrinsicHeight,Bitmap.Config.ARGB_8888)

        canvas.setBitmap(bitmap)
        this.apply {
            setBounds(0,0,this.intrinsicWidth,this.intrinsicHeight)
            draw(canvas)
        }

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }



    override fun setAlpha(alpha: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getOpacity(): Int = PixelFormat.OPAQUE

    override fun setColorFilter(colorFilter: ColorFilter?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}