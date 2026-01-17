package com.example.mviexample.domain.repository

import com.example.mviexample.domain.model.Post

interface PostRepository {
    suspend fun getPosts(): Result<List<Post>>
}