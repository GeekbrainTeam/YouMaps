package com.amk.listcoord.presenter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amk.core.Repository
import com.amk.core.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCoordinatesViewModel @Inject constructor(
    private val repository: Repository
): ViewModel()  {

    private val _state = MutableLiveData<Result>()
    val state: LiveData<Result>
        get() = _state

    init{
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.value = repository.getListCoordinates()
            Log.d("MKV2", "loadData:2 ")
        }
    }
}