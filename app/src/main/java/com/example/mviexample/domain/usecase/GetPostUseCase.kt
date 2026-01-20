package com.example.mviexample.domain.usecase

import com.example.mviexample.domain.repository.PostRepository
import com.example.mviexample.presentation.posts.PostsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    operator fun invoke(): Flow<PostsState> = flow {
        // 1. Emit Loading state immediately.
        emit(PostsState.Loading)

        // 2. Get the result from the repository.
        val result = postRepository.getPosts()

        // 3. Emit Success or Error based on the result.
        result.fold(
            onSuccess = { posts -> emit(PostsState.Success(posts)) },
            onFailure = { error -> emit(PostsState.Error(error.message ?: "Unknown error")) }
        )
    }
}