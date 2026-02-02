package com.example.mviexample.presentation.posts

import com.example.mviexample.domain.model.Post

/*sealed interface PostsState {
    data object Loading : PostsState
    data class Success(val posts: List<Post>) : PostsState
    data class Error(val message: String) : PostsState
}*/

sealed class PostsEvent {
    object LoadPosts : PostsEvent()
    object Retry : PostsEvent()
    data class PostClicked(val post: Post) : PostsEvent()
    data class QuickViewClicked(val post: Post) : PostsEvent()
    data class AddToWishListClicked(val post: Post) : PostsEvent()
}

sealed class PostsEffect {
    data class ShowError(val message: String) : PostsEffect()
    data class NavigateToPostDetail(val post: Post) : PostsEffect()
    data class NavigateToQuickScreen(val post: Post) : PostsEffect()
}

data class PostsState(
    var isLoading: Boolean = false,
    var data: List<Post>? = null,
    var error: String = ""
)