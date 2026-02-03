package com.example.mviexample.presentation.posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.mviexample.domain.model.Post
import com.example.mviexample.presentation.components.CustomIconButton
import com.example.mviexample.presentation.posts.mvi.PostsEffect
import com.example.mviexample.presentation.posts.mvi.PostsEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsScreen(
    viewModel: PostsViewModel = hiltViewModel(),
    onNavigateToDetail: (Post) -> Unit,
    onNavigateToQuickScreen: (Post) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is PostsEffect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message)
                }

                is PostsEffect.NavigateToPostDetail -> {
                    onNavigateToDetail(effect.post)
                }

                is PostsEffect.NavigateToQuickScreen -> {
                    onNavigateToQuickScreen(effect.post)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Posts") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val data = state.data
            if (state.isLoading && data.isNullOrEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (data != null) {
                if (data.isEmpty()) {
                    Text(
                        text = "No posts available",
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    PostsList(
                        posts = data,
                        onPostClick = { post ->
                            viewModel.onEvent(PostsEvent.PostClicked(post))
                        },
                        onQuickViewClick = { post ->
                            viewModel.onEvent(PostsEvent.QuickViewClicked(post))
                        },
                        onAddToWishListClick = { post ->
                            viewModel.onEvent(PostsEvent.AddToWishListClicked(post))
                        }
                    )
                }
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            } else if (state.error.isNotEmpty()) {
                ErrorContent(
                    message = state.error,
                    onRetry = { viewModel.onEvent(PostsEvent.Retry) }
                )
            }
        }
    }
}

@Composable
fun PostsList(
    posts: List<Post>,
    onPostClick: (Post) -> Unit,
    onQuickViewClick: (Post) -> Unit,
    onAddToWishListClick: (Post) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        items(posts) { post ->
            PostImageItem(
                post = post,
                onPostClick = onPostClick,
                onQuickViewClick = onQuickViewClick,
                onAddToWishListClick = onAddToWishListClick
            )
            /*PostItem(
                post = post,
                onPostClick = onPostClick
            )*/
        }
    }
}

@Composable
fun PostItem(
    post: Post,
    onPostClick: (Post) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AsyncImage(
                model = "https://picsum.photos/seed/${post.id}/200/200",
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onPostClick(post) },
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = post.body,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "User ID: ${post.userId}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun PostImageItem(
    post: Post,
    onPostClick: (Post) -> Unit,
    onQuickViewClick: (Post) -> Unit,
    onAddToWishListClick: (Post) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        AsyncImage(
            model = "https://picsum.photos/seed/${post.id}/200/200",
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable { onPostClick(post) },
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.End
        ) {
            CustomIconButton(icon = Icons.Default.Add) { onQuickViewClick(post) }
            CustomIconButton(icon = Icons.Default.Home) { onAddToWishListClick(post) }
        }
    }
}

@Composable
fun ErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}
