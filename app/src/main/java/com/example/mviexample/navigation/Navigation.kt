package com.example.mviexample.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.mviexample.domain.model.Post
import com.example.mviexample.presentation.detail.PostDetailScreen
import com.example.mviexample.presentation.posts.ui.PostsScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.PostsRoute
    ) {
        composable<Routes.PostsRoute> {
            PostsScreen(
                onNavigateToDetail = {
                    val route = Routes.PostDetailRoute(
                        id = it.id,
                        userId = it.userId,
                        title = it.title,
                        body = it.body
                    )
                    navController.navigate(route)
                }
            )
        }

        composable<Routes.PostDetailRoute> { backStackEntry ->
            val detailRoute = backStackEntry.toRoute<Routes.PostDetailRoute>()
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