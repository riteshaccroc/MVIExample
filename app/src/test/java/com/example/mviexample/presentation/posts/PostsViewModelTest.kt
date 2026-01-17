package com.example.mviexample.presentation.posts

import app.cash.turbine.test
import com.example.mviexample.domain.model.Post
import com.example.mviexample.domain.usecase.GetPostsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PostsViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var getPostsUseCase: GetPostsUseCase
    private lateinit var viewModel: PostsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getPostsUseCase = mockk()
    }

    @Test
    fun `given success state, when LoadPosts intent is handled, then viewmodel emits correct states`() = runTest {
        // Given
        val posts = listOf(Post(1, 123, "title", "body"))
        coEvery { getPostsUseCase() } returns Result.success(posts)

        // When
        viewModel = PostsViewModel(getPostsUseCase)

        // Then
        viewModel.state.test {
            // With Unconfined dispatcher, we miss the initial state and start with the loading state
            assertEquals(PostsState(isLoading = true), awaitItem())
            assertEquals(PostsState(isLoading = false, posts = posts), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given failure state, when LoadPosts intent is handled, then viewmodel emits correct states and effect`() = runTest {
        // Given
        val errorMessage = "Error fetching posts"
        coEvery { getPostsUseCase() } returns Result.failure(RuntimeException(errorMessage))

        // When
        viewModel = PostsViewModel(getPostsUseCase)

        // Then
        launch {
            viewModel.state.test {
                // With Unconfined dispatcher, we miss the initial state and start with the loading state
                assertEquals(PostsState(isLoading = true), awaitItem())
                assertEquals(PostsState(isLoading = false, error = errorMessage), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
        launch {
            viewModel.effect.test {
                assertEquals(PostsEffect.ShowError(errorMessage), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}