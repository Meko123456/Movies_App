package com.merabk.moviesapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.merabk.moviesapp.data.service.MoviesApi
import com.merabk.moviesapp.data.usecase.GetAllMoviesUseCaseImpl
import com.merabk.moviesapp.domain.usecase.GetAllMoviesUseCase
import com.merabk.moviesapp.util.Constants.API_BASE_URL
import com.merabk.moviesapp.util.Constants.API_KEY
import com.merabk.moviesapp.util.Constants.API_KEY_NAME
import com.merabk.moviesapp.util.Constants.BASE_URL_NAME
import com.merabk.moviesapp.util.Constants.FALSE
import com.merabk.moviesapp.util.Constants.INCLUDE_ADULT
import com.merabk.moviesapp.util.Constants.LANGUAGE
import com.merabk.moviesapp.util.Constants.LANGUAGE_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named(BASE_URL_NAME)
    fun provideBaseUrl(): String = API_BASE_URL

    @Provides
    @Named(API_KEY_NAME)
    fun provideApiKey(): String = API_KEY

    /* @OptIn(ExperimentalSerializationApi::class)
     @Provides
     @Singleton
     fun provideJson(): Json =
         Json {
             ignoreUnknownKeys = true
             encodeDefaults = true
             explicitNulls = false
         }*/
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    fun provideRetrofitBuilder(
        @Named(BASE_URL_NAME) url: String,
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)

    @Provides
    fun provideMoviesService(
        retrofit: Retrofit
    ): MoviesApi = retrofit.create()

    @Provides
    fun provideInterceptor(
        @Named(API_KEY_NAME) apiKey: String
    ): Interceptor =
        Interceptor { chain ->
            val original = chain.request()
            val httpUrl = original.url.newBuilder()
                .addQueryParameter(API_KEY_NAME, apiKey)
                .addQueryParameter(INCLUDE_ADULT, FALSE)
                .addQueryParameter(LANGUAGE, LANGUAGE_KEY)
                .build()
            val requestBuilder: Request.Builder = original.newBuilder()
                .url(httpUrl)
            chain.proceed(requestBuilder.build())
        }

    @Provides
    fun provideOkHttpClient(header: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                addInterceptor(header)
            }.build()

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        builder: Retrofit.Builder,
    ): Retrofit = builder
        .client(
            client.newBuilder()
                .build()
        ).build()

    @Provides
    fun bindGetAllMoviesUseCase(
        getAllMoviesUseCase: GetAllMoviesUseCaseImpl
    ): GetAllMoviesUseCase = getAllMoviesUseCase

}