package com.example.mviexample.domain.usecase

import com.example.mviexample.domain.model.Post
import com.example.mviexample.domain.repository.PostRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke(): Result<List<Post>> {
        return repository.getPosts()
    }
}