package com.example.mviexample.presentation.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MviViewModel<S, I, E> {
    val state: StateFlow<S>
    val effect: Flow<E>

    fun handleIntent(intent: I)
}
