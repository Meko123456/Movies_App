package com.merabk.moviesapp.util

import dagger.Reusable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface Dispatchers {
    fun launchMain(scope: CoroutineScope, block: suspend CoroutineScope.() -> Unit): Job
    fun launchBackground(scope: CoroutineScope, block: suspend CoroutineScope.() -> Unit): Job
    fun launchDefault(scope: CoroutineScope, block: suspend CoroutineScope.() -> Unit): Job
    suspend fun changeToUi(block: suspend CoroutineScope.() -> Unit)

    @Reusable
    class DispatchersImpl @Inject constructor() : Dispatchers {
        override fun launchMain(
            scope: CoroutineScope, block: suspend CoroutineScope.() -> Unit
        ) = scope.launch(kotlinx.coroutines.Dispatchers.Main, block = block)

        override fun launchBackground(
            scope: CoroutineScope, block: suspend CoroutineScope.() -> Unit
        ) = scope.launch(kotlinx.coroutines.Dispatchers.IO, block = block)

        override fun launchDefault(
            scope: CoroutineScope, block: suspend CoroutineScope.() -> Unit
        ) = scope.launch(kotlinx.coroutines.Dispatchers.Default, block = block)

        override suspend fun changeToUi(block: suspend CoroutineScope.() -> Unit) {
            withContext(kotlinx.coroutines.Dispatchers.Main, block)
        }
    }
}
