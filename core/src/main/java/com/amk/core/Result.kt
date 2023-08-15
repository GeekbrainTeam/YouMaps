package com.amk.core

sealed interface Result {

    class Success(val data: List<CoordinateWithName>) : Result

    class Loading : Result

    class Error(error: Throwable) : Result
}