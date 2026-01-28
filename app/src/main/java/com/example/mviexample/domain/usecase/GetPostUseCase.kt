package com.example.mviexample.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.example.mviexample.data.remote.dto.post.toDomain
import com.example.mviexample.domain.model.Post
import com.example.mviexample.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    operator fun invoke(): Flow<PagingData<Post>> {
        return postRepository.getPosts()
            .map { pagingData ->
                pagingData.map { it.toDomain() }
            }
    }
}