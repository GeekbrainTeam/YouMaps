package com.amk.youmaps.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.amk.core.LATITUDE_KEY
import com.amk.core.LONGITUDE_KEY
import com.amk.core.NAME_POINT_KEY
import com.amk.core.REQUEST_COORDINATE_KEY
import com.amk.youmaps.R
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
        val colorState = getColorState()
        with(binding) {
            latitudeEditLayout.setBoxStrokeColorStateList(colorState)
            longitudeEditLayout.setBoxStrokeColorStateList(colorState)
            nameEditLayout.setBoxStrokeColorStateList(colorState)
            latitude?.let { latitudeEditField.setText(it.toString()) }
            longitude?.let { longitudeEditField.setText(it.toString()) }
            save.setOnClickListener {
                setFragmentResult(
                    REQUEST_COORDINATE_KEY,
                    bundleOf(
                        NAME_POINT_KEY to nameEditField.text.toString(),
                        LATITUDE_KEY to latitudeEditField.text.toString(),
                        LONGITUDE_KEY to longitudeEditField.text.toString(),
                    )
                )
                dismiss()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun getColorState() = ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_active),
            intArrayOf(android.R.attr.state_focused),
            intArrayOf(-android.R.attr.state_focused),
            intArrayOf(android.R.attr.state_hovered),
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf(-android.R.attr.state_enabled)
        ),
        intArrayOf(
            requireActivity().getColor(R.color.light_gray),
            requireActivity().getColor(R.color.light_gray),
            requireActivity().getColor(R.color.light_gray),
            requireActivity().getColor(R.color.light_gray),
            requireActivity().getColor(R.color.light_gray),
            requireActivity().getColor(R.color.light_gray),
        )
    )

    private companion object{
        private const val LATITUDE = "Latitude"
        private const val LONGITUDE = "Longitude"
    }
}