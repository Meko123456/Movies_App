package com.merabk.moviesapp.data.usecase

import com.merabk.moviesapp.domain.model.AllMoviesDomainModel
import com.merabk.moviesapp.domain.repo.MoviesRepository
import com.merabk.moviesapp.domain.usecase.GetAllMoviesUseCase
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetAllMoviesUseCaseImpl @Inject constructor(private val repo: MoviesRepository) :
    GetAllMoviesUseCase {
    override suspend fun invoke(): Result<List<AllMoviesDomainModel>> = repo.getAllMoviesList()
}