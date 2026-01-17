package com.example.mviexample.presentation.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mviexample.domain.usecase.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase
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
            is PostsEvent.LoadPosts, is PostsEvent.Retry -> loadPosts()
            is PostsEvent.PostClicked -> {
                viewModelScope.launch {
                    _effect.emit(PostsEffect.NavigateToPostDetail(event.post))
                }
            }
        }
    }

    private fun loadPosts() {
        viewModelScope.launch {
            _state.value = PostsState.Loading

            getPostsUseCase().fold(
                onSuccess = { posts ->
                    _state.value = PostsState.Success(posts)
                },
                onFailure = { error ->
                    val errorMessage = error.message ?: "Unknown error"
                    _state.value = PostsState.Error(errorMessage)
                    _effect.emit(PostsEffect.ShowError(errorMessage))
                }
            )
        }
    }
}
