package com.merabk.moviesapp.data.service

import com.merabk.moviesapp.data.model.AllMoviesApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("discover/tv")
    suspend fun getAllMoviesList(): Response<AllMoviesApiModel>

    @GET("search/movie?")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
    ): Response<String>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
    ): Response<Double>
}