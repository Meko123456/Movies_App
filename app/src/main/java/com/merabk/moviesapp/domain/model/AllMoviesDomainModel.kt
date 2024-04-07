package com.merabk.moviesapp.domain.model

data class AllMoviesDomainModel(
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String,
    val vote_average: Double,
)
