package com.merabk.moviesapp.data.model

//@Serializable
data class AllMoviesApiModel(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)