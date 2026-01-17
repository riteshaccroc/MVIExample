package com.example.mviexample.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.mviexample.domain.model.Post
import com.example.mviexample.presentation.detail.PostDetailScreen
import com.example.mviexample.presentation.posts.PostsScreen
import kotlinx.serialization.Serializable

// Type-safe routes using Kotlin Serialization
@Serializable
object PostsRoute

@Serializable
data class PostDetailRoute(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = PostsRoute
    ) {
        composable<PostsRoute> {
            PostsScreen(
                onPostClick = { post ->
                    navController.navigate(
                        PostDetailRoute(
                            id = post.id,
                            userId = post.userId,
                            title = post.title,
                            body = post.body
                        )
                    )
                }
            )
        }

        composable<PostDetailRoute> { backStackEntry ->
            val detailRoute = backStackEntry.toRoute<PostDetailRoute>()
            val post = Post(
                id = detailRoute.id,
                userId = detailRoute.userId,
                title = detailRoute.title,
                body = detailRoute.body
            )

            PostDetailScreen(
                post = post,
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}