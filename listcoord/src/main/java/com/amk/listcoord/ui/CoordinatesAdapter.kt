package com.amk.listcoord.ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amk.core.CoordinateWithName
import com.amk.listcoord.R

class CoordinatesAdapter(
    private val data: List<CoordinateWithName>
) : RecyclerView.Adapter<CoordinatesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_coordinate, viewGroup, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("MKV2", "onBindViewHolder: position${position}")
        holder.textView.text = "Точка ${data[position]}"
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textView: TextView by lazy {
            view.findViewById(R.id.title)
        }
    }

    override fun getItemCount() = data.size
}