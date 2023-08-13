package com.amk.core.di

import com.amk.core.FakeRepository
import com.amk.core.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object Module {

    @Provides
    fun provideRepository(repository: FakeRepository): Repository = repository
}