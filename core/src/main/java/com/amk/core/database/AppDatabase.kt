package com.amk.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CoordinateDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coordinateDao(): CoordinateDao
}