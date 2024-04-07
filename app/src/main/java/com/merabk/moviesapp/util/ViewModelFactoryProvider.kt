package com.merabk.moviesapp.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object ViewModelFactoryProvider {

    fun <P : ViewModel?> provideViewModel(predicate: () -> P): ViewModelProvider.Factory =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                modelClass.cast(predicate()) as T
        }

    inline fun <reified P : ViewModel?> Fragment.factoryViewModel(noinline predicate: () -> P): Lazy<P> =
        this.viewModels { provideViewModel { predicate() } }
}