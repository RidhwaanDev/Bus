package com.example.busplaygroundkt.ui.components.recycler_components

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.busplaygroundkt.R
import com.example.busplaygroundkt.data.model.SingleBusListItem

class BusListAdapter(val onClick : (SingleBusListItem) -> Unit) : RecyclerView.Adapter<BusListItemViewHolder>(){

    var items : List<SingleBusListItem> = emptyList()

    fun loadItems(newItems: List<SingleBusListItem>){
        items = newItems
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusListItemViewHolder
            = BusListItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rl_single_bus_layout,parent,false))

    override fun onBindViewHolder(holder: BusListItemViewHolder, position: Int) {
        holder.busitem = items[position]
        holder.view.setOnClickListener{onClick(items[position])}
    }
}