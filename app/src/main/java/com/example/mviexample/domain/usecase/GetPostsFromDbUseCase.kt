package com.example.mviexample.domain.usecase

import com.example.mviexample.domain.model.Post
import com.example.mviexample.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostsFromDbUseCase @Inject constructor(private val repository: PostRepository) {
//    operator fun invoke(): Flow<List<Post>> = repository.getPostsFromDb()
}