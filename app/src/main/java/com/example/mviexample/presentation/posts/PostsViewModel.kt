package com.example.mviexample.presentation.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mviexample.domain.usecase.GetPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPostUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<PostsState>(PostsState.Loading)
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<PostsEffect>()
    val effect = _effect.asSharedFlow()

    init {
        loadPosts()
    }

    fun onEvent(event: PostsEvent) {
        when (event) {
            PostsEvent.LoadPosts, PostsEvent.Retry -> loadPosts()
            is PostsEvent.PostClicked -> {
                viewModelScope.launch {
                    _effect.emit(PostsEffect.NavigateToPostDetail(event.post))
                }
            }
        }
    }

    private fun loadPosts() {
        getPostsUseCase()
            .onEach { newState ->
                // 1. Update the state with each emission from the use case flow.
                _state.value = newState

                // 2. If the new state is an error, also emit a side effect.
                if (newState is PostsState.Error) {
                    _effect.emit(PostsEffect.ShowError(newState.message))
                }
            }
            .launchIn(viewModelScope)
    }
}
