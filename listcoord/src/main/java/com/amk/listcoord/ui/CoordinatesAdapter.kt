package com.amk.listcoord.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.amk.core.CoordinateWithName
import com.amk.listcoord.R
import com.amk.listcoord.databinding.ItemCoordinateBinding

class CoordinatesAdapter(
    private val data: List<CoordinateWithName>
) : RecyclerView.Adapter<CoordinatesAdapter.ViewHolder>() {

    private lateinit var itemBinding: ItemCoordinateBinding
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        itemBinding =
            ItemCoordinateBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(itemBinding.root)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(coordinateWithName: CoordinateWithName) {
            itemBinding.title.text =
                view.context.getStrings(R.string.title_coordinate, coordinateWithName.name)
            itemBinding.coordinate.text = view.context.getStrings(
                R.string.coordinate,
                coordinateWithName.latitude.toString(),
                coordinateWithName.longitude.toString(),
            )
        }
    }

    private fun Context.getStrings(@StringRes stringId: Int, vararg formatArgs: Any): String =
        getString(stringId, *formatArgs)

    override fun getItemCount() = data.size
}