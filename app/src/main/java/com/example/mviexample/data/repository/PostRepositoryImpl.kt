package com.example.mviexample.data.repository

import com.example.mviexample.data.db.PostDao
import com.example.mviexample.data.remote.ApiService
import com.example.mviexample.data.remote.dto.toPostList
import com.example.mviexample.domain.model.Post
import com.example.mviexample.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val apiService: ApiService, private val postDao: PostDao
) : PostRepository {
    override suspend fun getPosts(): Result<List<Post>> {
        return try {
            val response = apiService.getPosts()
            val posts = response.toPostList()
            Result.success(posts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getPostsFromDb(): Flow<List<Post>> = postDao.getAllIPosts()

    override suspend fun insertAllPosts(postList: List<Post>) {
        postDao.insertAllPosts(postList)
    }
}