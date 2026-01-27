package com.example.mviexample.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.mviexample.domain.model.Post
import com.example.mviexample.presentation.detail.PostDetailScreen
import com.example.mviexample.presentation.posts.PostsScreen
import com.example.mviexample.presentation.webview.CartScreen
import com.example.mviexample.presentation.webview.WebViewSafeScreen
import kotlinx.serialization.Serializable

// Type-safe routes using Kotlin Serialization
@Serializable
object PostsRoute

@Serializable
object WebViewRoute
@Serializable
object CartRoute

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
        startDestination = WebViewRoute
    ) {
        composable<PostsRoute> {
            PostsScreen(
                onNavigateToDetail = {
                    val route = PostDetailRoute(
                        id = it.id,
                        userId = it.userId,
                        title = it.title,
                        body = it.body
                    )
                    navController.navigate(route)
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

        composable<WebViewRoute> {
            WebViewSafeScreen(onButtonClick = { navController.navigate(CartRoute) })
//            WebviewScreen()
        }

        composable<CartRoute> {
            CartScreen()
//            WebviewScreen()
        }
    }
}