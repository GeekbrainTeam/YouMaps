package com.amk.core.di

import android.content.Context
import androidx.room.Room
import com.amk.core.Repository
import com.amk.core.RepositoryImpl
import com.amk.core.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CoreModule {

    @Provides
    fun provideRepository(repository: RepositoryImpl): Repository = repository

    @Singleton
    @Provides
    fun provideCoordinateDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(app, AppDatabase::class.java, "coordinateDataBase").build()

    @Singleton
    @Provides
    fun provideCoordinateDao(db: AppDatabase) = db.coordinateDao()
}