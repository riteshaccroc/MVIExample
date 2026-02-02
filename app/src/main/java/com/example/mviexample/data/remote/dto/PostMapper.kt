package com.example.mviexample.data.remote.dto

import com.example.mviexample.domain.model.Post

fun List<PostDto>.toPostList() = map { dto->
    Post(
        id = dto.postId,
        userId = dto.userId,
        title = dto.title,
        body = dto.body
    )
}