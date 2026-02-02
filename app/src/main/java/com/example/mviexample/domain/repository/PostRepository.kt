package com.example.mviexample.domain.repository

import com.example.mviexample.data.db.PostDao
import com.example.mviexample.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getPosts(): Result<List<Post>>
    fun getPostsFromDb(): Flow<List<Post>>
    suspend fun insertAllPosts(postList: List<Post>)
}