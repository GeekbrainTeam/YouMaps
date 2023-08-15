package com.amk.maps

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.amk.core.CoordinateWithName
import com.amk.core.MainViewModel
import com.amk.core.Result
import com.amk.maps.presentation.MapsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : Fragment(), OnMapReadyCallback {

    private val viewModel: MapsViewModel by viewModels()
    private val activityViewModel:MainViewModel by activityViewModels ()

    private var zoomMap: Float = 11f

    private lateinit var map: GoogleMap

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
            Log.d("MKV2", "observe:result ${result} ")
            when (result) {
                is Result.Error -> {}
                is Result.Loading -> {}
                is Result.Success -> {
                    val coordinate = result.data.lastOrNull()?:COORDINATE_WithName_DEFAULT
                    val position = LatLng(coordinate.latitude, coordinate.longitude)
                    map.addMarker(MarkerOptions().position(position).title("Marker in default"))
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, zoomMap))
                }
            }
        }
        googleMap.setOnCameraIdleListener {
            extracted(googleMap)
        }
//        googleMap.setOnCameraMoveListener {
//            Log.d("MKV2", "setOnCameraMoveListener ")
////            extracted(googleMap)
//        }
//        googleMap.setOnCameraMoveListener {
//            Log.d("MKV2", "setOnCameraMoveListener ")
//        }
//        googleMap.setOnCameraMoveStartedListener {
//            Log.d("MKV2", "setOnCameraMoveStartedListener ")
//        }
//        googleMap.setOnCircleClickListener {
//            Log.d("MKV2", "setOnCircleClickListener ")
//        }
//        googleMap.setOnGroundOverlayClickListener {
//            Log.d("MKV2", "setOnGroundOverlayClickListener ")
//        }
//        googleMap.setOnInfoWindowClickListener {
//            Log.d("MKV2", "setOnInfoWindowClickListener ")
//        }
//        googleMap.setOnInfoWindowCloseListener {
//            Log.d("MKV2", "setOnInfoWindowCloseListener ")
//        }
//        googleMap.setOnInfoWindowLongClickListener {
//            Log.d("MKV2", "setOnInfoWindowLongClickListener ")
//        }
//        googleMap.setOnMapClickListener {
//            Log.d("MKV2", "setOnMapClickListener ")
//        }
//        googleMap.setOnPoiClickListener {
//            Log.d("MKV2", "setOnPoiClickListener ")
//        }
//        googleMap.setOnPolylineClickListener {
//            Log.d("MKV2", "setOnPolylineClickListener ")
//        }
    }

    private fun extracted(googleMap: GoogleMap) {
        val position = googleMap.projection.visibleRegion.latLngBounds.center
        activityViewModel.onChangeCoordinate(
            lat = position.latitude,
            long = position.longitude,
        )
    }

    private companion object {
        private val COORDINATE_WithName_DEFAULT = CoordinateWithName(
            name = "name",
            latitude = 59.915675,
            longitude = 30.302950,
        )
    }
}