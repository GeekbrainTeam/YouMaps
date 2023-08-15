package com.amk.core

import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getListCoordinates(): Flow<Result>

    suspend fun saveNewCoordinate(coordinateWithName: CoordinateWithName)
}
