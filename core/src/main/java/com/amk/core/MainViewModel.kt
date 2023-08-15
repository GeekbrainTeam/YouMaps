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
class MainViewModel @Inject constructor() : ViewModel() {

    private val _selectedCoordinate = MutableStateFlow(COORDINATE_DEFAULT)
    val selectedCoordinateWithName: StateFlow<Coordinate>
        get() = _selectedCoordinate

    fun onChangeCoordinate(lat: Double, long: Double) {
        _selectedCoordinate.value = Coordinate(
            latitude = lat,
            longitude = long,
        )
    }

    private companion object {

        private val COORDINATE_DEFAULT = Coordinate(
            latitude = 59.9156,
            longitude = 30.3029,
        )
    }
}