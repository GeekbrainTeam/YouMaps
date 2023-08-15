package com.amk.maps.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amk.core.Repository
import com.amk.core.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableLiveData<Result>()
    val state: LiveData<Result>
        get() = _state

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            repository
                .getListCoordinates()
                .collect { result ->
                    _state.value = result
                }
        }
    }
}