package com.example.mviexample.presentation.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.mviexample.domain.usecase.GetPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPostUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PostsState())
    val state = _state.asStateFlow()

    fun send(intent: PostsIntent) {
        when (intent) {
            PostsIntent.LoadPosts -> loadPosts()
        }
    }

    private fun loadPosts() {
        val flow = getPostsUseCase()
            .cachedIn(viewModelScope)

        _state.update {
            it.copy(posts = flow)
        }
    }
}
