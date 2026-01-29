package com.example.mviexample.presentation.posts.mvi

import com.example.mviexample.domain.model.Post

sealed class PostsEvent {
    object LoadPosts : PostsEvent()
    object Retry : PostsEvent()
    data class PostClicked(val post: Post) : PostsEvent()
}

sealed class PostsEffect {
    data class ShowError(val message: String) : PostsEffect()
    data class NavigateToPostDetail(val post: Post) : PostsEffect()
}

data class PostsState(
    var isLoading: Boolean = false,
    var data: List<Post>? = null,
    var error: String = ""
)