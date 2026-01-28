package com.example.mviexample.domain.repository

import androidx.paging.PagingData
import com.example.mviexample.data.remote.dto.post.PostDto
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<PagingData<PostDto>>
}