package com.example.mviexample.presentation.pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mviexample.data.ApiStatus
import com.example.mviexample.domain.usecase.GetPokeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokeViewModel @Inject constructor(
    private val getPokeUseCase: GetPokeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<PokeState>(PokeState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<PokeEffect>()
    val effect = _effect.asSharedFlow()

    init {
        loadPokemon()
    }

    fun onEvent(event: PokeEvent) {
        when (event) {
            PokeEvent.LoadPosts, PokeEvent.Retry -> loadPokemon()

            is PokeEvent.PostClicked -> viewModelScope.launch { /*_effect.emit(PokeEffect.NavigateToPostDetail(event.poke))*/ }
        }
    }

    private fun loadPokemon() {
        /*getPokeUseCase(10)
            .flowOn(IO)
            .onEach { newState ->
                when (newState) {
                    is ApiStatus.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is ApiStatus.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                data = newState.data,
                                error = ""
                            )
                        }
                    }

                    is ApiStatus.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false
                            )
                        }

                        _effect.emit(
                            PokeEffect.ShowError(
                                newState.message ?: "An unexpected error has occurred"
                            )
                        )
                    }
                }
            }.launchIn(viewModelScope)*/
    }
}
