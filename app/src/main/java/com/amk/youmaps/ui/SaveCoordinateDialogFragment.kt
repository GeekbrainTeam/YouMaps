package com.amk.youmaps.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.amk.core.LATITUDE_KEY
import com.amk.core.LONGITUDE_KEY
import com.amk.core.NAME_POINT_KEY
import com.amk.core.REQUEST_COORDINATE_KEY
import com.amk.youmaps.databinding.FragmentSaveCoordinateDialogBinding

class SaveCoordinateDialogFragment : DialogFragment() {

    private var latitude: Double? = null
    private var longitude: Double? = null

    private lateinit var binding: FragmentSaveCoordinateDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            latitude = it.getDouble(LATITUDE)
            longitude = it.getDouble(LONGITUDE)
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
            latitude?.let { latitudeEditView.setText(it.toString()) }
            longitude?.let { longitudeEditView.setText(it.toString()) }
            save.setOnClickListener {
                setFragmentResult(
                    REQUEST_COORDINATE_KEY,
                    bundleOf(
                        NAME_POINT_KEY to name.text.toString(),
                        LATITUDE_KEY to latitudeEditView.text.toString(),
                        LONGITUDE_KEY to longitudeEditView.text.toString(),
                    )
                )
                dismiss()
            }
        }
    }

    private companion object{
        private const val LATITUDE = "Latitude"
        private const val LONGITUDE = "Longitude"
    }
}