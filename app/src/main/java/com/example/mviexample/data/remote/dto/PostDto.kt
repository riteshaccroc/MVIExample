package com.example.mviexample.data.remote.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//TODO:check if it should be Post or PostDto
@Entity(tableName = "posts")
data class PostDto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @SerializedName("id") val postId: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String
)