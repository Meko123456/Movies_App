package com.merabk.moviesapp.data.repo

import com.merabk.moviesapp.data.mapper.AllFilmsMapper
import com.merabk.moviesapp.data.service.MoviesApi
import com.merabk.moviesapp.domain.model.AllMoviesDomainModel
import com.merabk.moviesapp.domain.repo.MoviesRepository
import com.merabk.moviesapp.util.callAndMap
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val service: MoviesApi,
    private val allFilmsMapper: AllFilmsMapper,
) : MoviesRepository {
    override suspend fun getAllMoviesList(): Result<List<AllMoviesDomainModel>> =
        callAndMap(
            serviceCall = {
                service.getAllMoviesList()
                          },
            mapper = {
                    response -> allFilmsMapper.map(response.results)
            }
        )


}