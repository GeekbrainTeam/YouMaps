package com.amk.core

sealed interface Result {

    class Success(val data: List<Coordinate>) : Result

    class Loading : Result

    class Error : Result
}