package com.amk.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CoordinateDao {

    @Query("SELECT * FROM coordinates")
    fun getAllCoordinate(): Flow<List<CoordinateDB>>

    @Insert(entity = CoordinateDB::class, onConflict = OnConflictStrategy.REPLACE)
    fun addCoordinate(coordinateDB: CoordinateDB)
}