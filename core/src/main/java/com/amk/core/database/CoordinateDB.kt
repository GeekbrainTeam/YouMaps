package com.amk.core.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coordinates")
data class CoordinateDB(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val uid: Int = 0,

    @ColumnInfo(name = "name_field") val name: String,

    @ColumnInfo(name = "latitude") val latitude: String,

    @ColumnInfo(name = "longitude") val longitude: String,
)