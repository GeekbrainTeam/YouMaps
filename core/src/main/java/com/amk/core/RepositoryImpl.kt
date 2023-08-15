package com.amk.core

import com.amk.core.database.CoordinateDB
import com.amk.core.database.CoordinateDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.math.RoundingMode
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val coordinateDao: CoordinateDao
) : Repository {

    override suspend fun getListCoordinates(): Flow<Result> {
        return coordinateDao
            .getAllCoordinate()
            .catch { error ->
                Result.Error(error)
            }
            .map { coordinates ->
                Result.Success(
                    coordinates
                        .map {
                            CoordinateWithName(
                                name = it.name,
                                latitude = it.latitude.toDouble(),
                                longitude = it.longitude.toDouble(),
                            )
                        }
                )
            }
    }

    override suspend fun saveNewCoordinate(coordinateWithName: CoordinateWithName) {
        coordinateDao.addCoordinate(coordinateWithName.mapToCoordinateDB())
    }
}

private fun CoordinateWithName.mapToCoordinateDB(): CoordinateDB =
    CoordinateDB(
        name = name,
        latitude = latitude.toRoundString(),
        longitude = longitude.toRoundString(),
    )

private fun Double.toRoundString() =
    this.toBigDecimal().setScale(4, RoundingMode.UP).toString()
