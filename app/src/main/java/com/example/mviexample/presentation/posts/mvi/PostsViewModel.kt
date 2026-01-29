package com.example.mviexample.presentation.posts.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mviexample.common.ApiStatus
import com.example.mviexample.domain.usecase.GetPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
class PostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPostUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PostsState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<PostsEffect>()
    val effect = _effect.asSharedFlow()

    init {
        loadPosts()
    }

    fun onEvent(event: PostsEvent) {
        when (event) {
            is PostsEvent.LoadPosts, PostsEvent.Retry -> loadPosts()

            is PostsEvent.PostClicked -> viewModelScope.launch {
                _effect.emit(
                    PostsEffect.NavigateToPostDetail(
                        event.post
                    )
                )
            }
        }
    }

    private fun loadPosts() {
        getPostsUseCase()
            .flowOn(Dispatchers.IO)
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
                            PostsEffect.ShowError(
                                newState.message ?: "An unexpected error has occurred"
                            )
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }
}