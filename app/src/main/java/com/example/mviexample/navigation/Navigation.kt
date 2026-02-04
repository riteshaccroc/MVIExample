package com.example.mviexample.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.mviexample.domain.model.Post
import com.example.mviexample.presentation.dataStore.FirstScreen
import com.example.mviexample.presentation.dataStore.HomeScreen
import com.example.mviexample.presentation.detail.PostDetailScreen
import com.example.mviexample.presentation.posts.PostsScreen
import com.example.mviexample.presentation.posts.QuickViewScreen
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

@Serializable
data class QuickViewRoute(
    //val item: Post,//TODO:not able to pass custom object,check
    val item: String,
    val sizeGridList: List<String>
)

@Serializable
object FirstScreenRoute

@Serializable
object HomeScreenRoute

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = FirstScreenRoute
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
                },
                onNavigateToQuickScreen = { post ->
                    val route = QuickViewRoute(
                        item = post.title,
                        sizeGridList = listOf(
                            "XS", "S", "M", "L", "XL", "XXL", "XXXL"
                        )
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

        composable<QuickViewRoute> { backStackEntry ->
            val quickRoute = backStackEntry.toRoute<QuickViewRoute>()
            QuickViewScreen(
                item = quickRoute.item,
                sizeGridList = quickRoute.sizeGridList
            )
        }

        composable<FirstScreenRoute> {
            FirstScreen(){
                navController.navigate(HomeScreenRoute)
            }
        }

        composable<HomeScreenRoute> {
            HomeScreen()
        }
    }
}