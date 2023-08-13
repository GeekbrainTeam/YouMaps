package com.amk.core

import javax.inject.Inject

interface Repository {

    suspend fun getListCoordinates(): Result
}

class FakeRepository @Inject constructor() : Repository {

    override suspend fun getListCoordinates(): Result =
        Result.Success(
            listOf(
                Coordinate(10.0, 11.0),
                Coordinate(20.0, 12.0),
                Coordinate(30.0, 13.0),
                Coordinate(40.0, 14.0),
                Coordinate(50.0, 15.0),
            )
        )
}