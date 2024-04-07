package com.merabk.moviesapp.domain.usecase

import com.merabk.moviesapp.domain.model.AllMoviesDomainModel

interface GetAllMoviesUseCase {
    suspend operator fun invoke(): Result<List<AllMoviesDomainModel>>
}

/*

class GetAllMoviesUseCase @Inject constructor(private val repo: MoviesRepository) {
    suspend operator fun invoke(): Result<List<AllMoviesDomainModel>> = repo.getAllMoviesList()
}*/

