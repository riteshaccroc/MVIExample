package com.example.mviexample.navigation

import kotlinx.serialization.Serializable

object Routes {
    @Serializable
    object PostsRoute

    @Serializable
    data class PostDetailRoute(
        val id: Int,
        val userId: Int,
        val title: String,
        val body: String
    )
}