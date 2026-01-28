package com.example.mviexample.data.remote.dto.post

import com.example.mviexample.domain.model.Post
import com.google.gson.annotations.SerializedName

data class PostDto(
    @SerializedName("id") val id: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String
)

fun PostDto.toDomain(): Post {
    return Post(id, userId, title, body)
}