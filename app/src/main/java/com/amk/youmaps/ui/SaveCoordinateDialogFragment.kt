package com.amk.youmaps.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.amk.core.CoordinateWithName
import com.amk.core.MainViewModel
import com.amk.youmaps.databinding.FragmentSaveCoordinateDialogBinding

private const val ARG_PARAM1 = "Latitude"
private const val ARG_PARAM2 = "Longitude"

class SaveCoordinateDialogFragment : DialogFragment() {

    private var param1: Double? = null
    private var param2: Double? = null

    private val activityViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentSaveCoordinateDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getDouble(ARG_PARAM1)
            param2 = it.getDouble(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSaveCoordinateDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            param1?.let { latitude.setText(it.toString()) }
            param2?.let { longitude.setText(it.toString()) }
            save.setOnClickListener {
                Log.d("MKV2", "name:${name.text.toString()} ${name.transitionName}")
                activityViewModel.saveNewCoordinate(
                    CoordinateWithName(
                        name = name.text.toString(),
                        latitude = latitude.text.toString().toDouble(),
                        longitude = longitude.text.toString().toDouble(),
                    )
                )
            }
        }
    }
}