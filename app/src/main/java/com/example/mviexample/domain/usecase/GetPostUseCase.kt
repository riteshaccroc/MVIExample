package com.example.mviexample.domain.usecase

import com.example.mviexample.data.ApiStatus
import com.example.mviexample.domain.model.Post
import com.example.mviexample.domain.repository.PostRepository
import com.example.mviexample.presentation.posts.PostsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    operator fun invoke(): Flow<ApiStatus<List<Post>>> = flow {
        emit(ApiStatus.Loading())

        val result = postRepository.getPosts()

        result.fold(
            onSuccess = { posts -> emit(ApiStatus.Success(posts)) },
            onFailure = { error -> emit(ApiStatus.Error(error.message ?: "Unknown error")) }
        )
    }
}