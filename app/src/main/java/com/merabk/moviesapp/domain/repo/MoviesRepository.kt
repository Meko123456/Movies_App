package com.merabk.moviesapp.domain.repo

import com.merabk.moviesapp.domain.model.AllMoviesDomainModel


interface MoviesRepository {
    suspend fun getAllMoviesList(): Result<List<AllMoviesDomainModel>>
}