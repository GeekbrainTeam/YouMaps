package com.amk.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.amk.core.CoordinateWithName
import com.amk.core.LATITUDE_KEY
import com.amk.core.LONGITUDE_KEY
import com.amk.core.MainViewModel
import com.amk.core.NAME_POINT_KEY
import com.amk.core.REQUEST_COORDINATE_KEY
import com.amk.core.REQUEST_ZOOM_KEY
import com.amk.core.Result
import com.amk.core.ZOOM_KEY
import com.amk.maps.presentation.MapsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import java.math.RoundingMode

@AndroidEntryPoint
class MapsFragment : Fragment(), OnMapReadyCallback {

    private val viewModel: MapsViewModel by viewModels()
    private val activityViewModel: MainViewModel by activityViewModels()

    private lateinit var map: GoogleMap
    private var zoom: Float = 11f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(REQUEST_ZOOM_KEY) { _, bundle ->
            zoom = bundle.getString(ZOOM_KEY)?.toFloatOrNull() ?: zoom
        }
        setFragmentResultListener(REQUEST_COORDINATE_KEY) { _, bundle ->
            saveCoordinate(bundle)
        }
    }

    override fun onPause() {
        super.onPause()
        setFragmentResult(ZOOM_KEY, bundleOf("zoomKey" to zoom.toString()))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        viewModel.state.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> {}
                is Result.Loading -> {}
                is Result.Success -> {
                    val coordinate = result.data.lastOrNull() ?: COORDINATE_WithName_DEFAULT
                    val position = LatLng(coordinate.latitude, coordinate.longitude)
                    map.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            position,
                            zoom
                        )
                    )
                    map.addMarker(MarkerOptions().position(position))
                }
            }
        }
        googleMap.setOnCameraIdleListener {
            changeCoordinate(googleMap)
        }
        googleMap.setOnCameraMoveListener {
            zoom = googleMap.cameraPosition.zoom
        }
    }

    private fun saveCoordinate(bundle: Bundle) {
        val latitude: Double? = bundle.getString(LATITUDE_KEY)?.toDoubleOrNull()
        val longitude: Double? = bundle.getString(LONGITUDE_KEY)?.toDoubleOrNull()
        val name = bundle.getString(NAME_POINT_KEY)

        val isCoordinateExist =
            latitude.isNotNull() && longitude.isNotNull() && name?.isNotBlank() == true

        if (isCoordinateExist) {
            viewModel.saveNewCoordinate(
                CoordinateWithName(
                    name = name!!,
                    latitude = latitude!!,
                    longitude = longitude!!,
                )
            )
        }
    }

    private fun changeCoordinate(googleMap: GoogleMap) {
        val position = googleMap.projection.visibleRegion.latLngBounds.center
        activityViewModel.onChangeCoordinate(
            lat = position.latitude.round(),
            long = position.longitude.round(),
        )
    }

    private companion object {

        private val COORDINATE_WithName_DEFAULT = CoordinateWithName(
            name = "name",
            latitude = 59.9156,
            longitude = 30.3029,
        )
    }
}

private fun Double?.isNotNull(): Boolean = this != null

private fun Double.round() =
    this.toBigDecimal().setScale(4, RoundingMode.UP).toDouble()