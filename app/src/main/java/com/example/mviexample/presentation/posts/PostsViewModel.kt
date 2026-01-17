package com.example.mviexample.presentation.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mviexample.domain.usecase.GetPostsUseCase
import com.example.mviexample.presentation.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel(), MviViewModel<PostsState, PostsIntent, PostsEffect> {

    private val _state = MutableStateFlow(PostsState())
    override val state: StateFlow<PostsState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<PostsEffect>()
    override val effect: SharedFlow<PostsEffect> = _effect.asSharedFlow()

    init {
        handleIntent(PostsIntent.LoadPosts)
    }

    override fun handleIntent(intent: PostsIntent) {
        when (intent) {
            is PostsIntent.LoadPosts, PostsIntent.Retry -> loadPosts()
        }
    }

    private fun loadPosts() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            getPostsUseCase().fold(
                onSuccess = { posts ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        posts = posts,
                        error = null
                    )
                },
                onFailure = { error ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = error.message ?: "Unknown error"
                    )
                    _effect.emit(PostsEffect.ShowError(error.message ?: "Unknown error"))
                }
            )
        }
    }
}
