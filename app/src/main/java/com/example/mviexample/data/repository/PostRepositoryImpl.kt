package com.example.mviexample.data.repository

import com.example.mviexample.data.remote.ApiService
import com.example.mviexample.domain.model.Post
import com.example.mviexample.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PostRepository {

    override suspend fun getPosts(): Result<List<Post>> {
        return try {
            val response = apiService.getPosts()
            val posts = response.map { dto ->
                Post(
                    id = dto.id,
                    userId = dto.userId,
                    title = dto.title,
                    body = dto.body
                )
            }
            Result.success(posts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}