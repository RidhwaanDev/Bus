package com.example.busplaygroundkt.ui.components.recycler_components

import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.busplaygroundkt.R
import com.example.busplaygroundkt.data.model.SingleBusListItem
class BusListItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    var busitem : SingleBusListItem? = null
        // setter
    set(value){
        field = value
        view.findViewById<TextView>(R.id.tv_bus_name).text = value?.busName
    }

}