package com.example.mviexample.presentation.posts

import com.example.mviexample.domain.model.Post

sealed class PostsIntent {
    object LoadPosts : PostsIntent()
    object Retry : PostsIntent()
}

data class PostsState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
    val error: String? = null
)

sealed class PostsEffect {
    data class ShowError(val message: String) : PostsEffect()
}