package com.example.mviexample.data.remote.service

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mviexample.data.remote.dto.post.PostDto

class PostsPagingSource(
    private val api: ApiService
) : PagingSource<Int, PostDto>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, PostDto> {
        val page = params.key ?: 0
        val limit = params.loadSize

        return try {
            val posts = api.getPosts(start = page * limit, limit = limit)
            LoadResult.Page(
                data = posts,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (posts.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PostDto>): Int? =
        state.anchorPosition?.let { it / state.config.pageSize }
}