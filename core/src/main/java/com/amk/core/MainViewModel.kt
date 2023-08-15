package com.amk.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repositoryImpl: Repository,
) : ViewModel() {

    private val _selectedCoordinate = MutableStateFlow(COORDINATE_DEFAULT)
    val selectedCoordinateWithName: StateFlow<Coordinate>
        get() = _selectedCoordinate

    private var _zoomMap: Float = 11f
    val zoomMap: Float
        get() = _zoomMap

    fun onChangeCoordinate(lat: Double, long: Double) {
        _selectedCoordinate.value = Coordinate(
            latitude = lat,
            longitude = long,
        )
    }

    fun saveNewCoordinate(coordinateWithName: CoordinateWithName) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.saveNewCoordinate(coordinateWithName = coordinateWithName)
        }
    }

    fun setZoom(newZoomValue:Float){
        _zoomMap = newZoomValue
    }

    private companion object {

        private val COORDINATE_DEFAULT = Coordinate(
            latitude = 59.915675,
            longitude = 30.302950,
        )
    }
}