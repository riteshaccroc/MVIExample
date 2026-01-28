package com.example.mviexample.presentation.posts

import androidx.paging.PagingData
import com.example.mviexample.domain.model.Post
import kotlinx.coroutines.flow.Flow

sealed interface PostsIntent {
    object LoadPosts : PostsIntent
}

data class PostsState(
    val posts: Flow<PagingData<Post>>? = null
)

/*sealed class PostsIntent {
    object LoadPosts : PostsIntent()
    object Retry : PostsIntent()
    data class PostClicked(val post: Post) : PostsIntent()
}

sealed class PostsEffect {
    data class ShowError(val message: String) : PostsEffect()
    data class NavigateToPostDetail(val post: Post) : PostsEffect()
}

data class PostsState(
    var isLoading: Boolean = false,
    val data: PagingData<Post> = PagingData.empty(),
    var error: String = ""
)*/

