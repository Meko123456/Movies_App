package com.merabk.moviesapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.merabk.moviesapp.domain.model.AllMoviesDomainModel
import com.merabk.moviesapp.domain.usecase.GetAllMoviesUseCase
import com.merabk.moviesapp.util.Dispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val dispatchers: Dispatchers
) :
    ViewModel() {

    private val _allMoviesData
            : MutableStateFlow<DataState<List<AllMoviesDomainModel>>> =
        MutableStateFlow(DataState.Loading)
    val allMoviesData = _allMoviesData.asStateFlow()

    private val _showProgressBar = MutableStateFlow(false)
    val showProgressBar = _showProgressBar.asStateFlow()

    init {
        getAllMovies()
    }

    private fun getAllMovies() {
        dispatchers.launchBackground(viewModelScope) {
            _showProgressBar.emit(true)
            val result = getAllMoviesUseCase()
            result.onSuccess { data ->
                _allMoviesData.tryEmit(DataState.Success(data))
                _showProgressBar.emit(false)
            }
            result.onFailure {
                _allMoviesData.tryEmit(DataState.Error(it.message ?: "An error occurred"))
                _showProgressBar.emit(false)
            }
        }
    }
}