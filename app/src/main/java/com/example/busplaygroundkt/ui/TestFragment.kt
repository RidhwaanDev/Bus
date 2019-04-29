package com.example.busplaygroundkt.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.busplaygroundkt.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class TestFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map_test, container,false)
         val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view -> Snackbar.make(view,"Whats up dawg",Snackbar.LENGTH_SHORT).show() }
        return view
    }
}