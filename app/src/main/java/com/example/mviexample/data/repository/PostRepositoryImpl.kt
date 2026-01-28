package com.example.mviexample.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mviexample.data.remote.dto.post.PostDto
import com.example.mviexample.data.remote.service.ApiService
import com.example.mviexample.data.remote.service.PostsPagingSource
import com.example.mviexample.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PostRepository {

    override fun getPosts(): Flow<PagingData<PostDto>> =
        Pager(PagingConfig(pageSize = 20)) {
            PostsPagingSource(apiService)
        }.flow

    /*override suspend fun getPosts(): Result<List<Post>> {
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
    }*/
}