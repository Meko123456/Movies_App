package com.merabk.moviesapp.di

import com.merabk.moviesapp.data.mapper.AllFilmsMapper
import com.merabk.moviesapp.data.repo.MoviesRepositoryImpl
import com.merabk.moviesapp.domain.repo.MoviesRepository
import com.merabk.moviesapp.util.Dispatchers
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MoviesBindsModule {

    @Binds
   abstract fun bindDispatchers(
        dispatchersImpl: Dispatchers.DispatchersImpl
    ): Dispatchers

    @Binds
    @Singleton
    abstract  fun bindMyRepository(
        myRepositoryImpl: MoviesRepositoryImpl
    ): MoviesRepository

    @Binds
    abstract fun bindAllFilmsMapper(
        allFilmsMapper: AllFilmsMapper.AllFilmsMapperImpl
    ): AllFilmsMapper

}