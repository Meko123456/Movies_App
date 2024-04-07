package com.merabk.moviesapp.data.mapper

import com.merabk.moviesapp.data.model.Result
import com.merabk.moviesapp.domain.model.AllMoviesDomainModel
import com.merabk.moviesapp.util.Constants.IMAGE_BEGINNING
import dagger.Reusable
import javax.inject.Inject

interface AllFilmsMapper {
    fun map(allMoviesModel: List<Result>): List<AllMoviesDomainModel>

    @Reusable
    class AllFilmsMapperImpl @Inject constructor() : AllFilmsMapper {
        override fun map(allMoviesModel: List<Result>): List<AllMoviesDomainModel> =
            allMoviesModel.map {
                AllMoviesDomainModel(
                    id = it.id,
                    overview = it.overview,
                    name = it.name,
                    vote_average = it.vote_average,
                    poster_path = (IMAGE_BEGINNING + it.poster_path)
                )
            }
    }
}