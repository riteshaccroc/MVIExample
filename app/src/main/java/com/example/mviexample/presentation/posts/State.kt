package com.example.mviexample.presentation.posts

import com.example.mviexample.domain.model.Post

sealed interface PostsState {
    data object Loading : PostsState
    data class Success(val posts: List<Post>) : PostsState
    data class Error(val message: String) : PostsState
}

sealed class PostsEvent {
    object LoadPosts : PostsEvent()
    object Retry : PostsEvent()
    data class PostClicked(val post: Post) : PostsEvent()
}

sealed class PostsEffect {
    data class ShowError(val message: String) : PostsEffect()
    data class NavigateToPostDetail(val post: Post) : PostsEffect()
}
