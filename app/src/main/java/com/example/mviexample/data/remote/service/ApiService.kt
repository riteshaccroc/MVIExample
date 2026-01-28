package com.example.mviexample.data.remote.service

import com.example.mviexample.data.remote.dto.post.PostDto
import com.example.mviexample.domain.model.Post
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<PostDto>

    @GET("posts")
    suspend fun getPosts(
        @Query("_start") start: Int,
        @Query("_limit") limit: Int
    ): List<PostDto>
}