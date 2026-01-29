package com.example.mviexample.presentation.posts

import app.cash.turbine.test
import com.example.mviexample.domain.model.Post
import com.example.mviexample.domain.usecase.GetPostUseCase
import com.example.mviexample.presentation.posts.mvi.PostsViewModel
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

    private lateinit var getPostsUseCase: GetPostUseCase
    private lateinit var viewModel: PostsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getPostsUseCase = mockk()
    }

    /*@Test
    fun `given success result, when ViewModel is initialized, then emits Loading and Success states`() = runTest {
        // Given a successful result from the use case
        val posts = listOf(Post(1, 123, "title", "body"))
        coEvery { getPostsUseCase() } returns Result.success(posts)

        // When the ViewModel is created
        viewModel = PostsViewModel(getPostsUseCase)

        // Then the state flow should emit Loading, then Success
        viewModel.state.test {
            // The ViewModel's initial state is Loading, emitted immediately.
            assertEquals(PostsState.Loading, awaitItem())
            // After the use case returns, the state becomes Success.
            assertEquals(PostsState.Success(posts), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given failure result, when ViewModel is initialized, then emits Loading and Error states and a ShowError effect`() = runTest {
        // Given a failure result from the use case
        val errorMessage = "Error fetching posts"
        coEvery { getPostsUseCase() } returns Result.failure(RuntimeException(errorMessage))

        // When the ViewModel is created
        viewModel = PostsViewModel(getPostsUseCase)

        // Then the state flow should emit Loading then Error, and the effect flow should emit ShowError
        launch {
            viewModel.state.test {
                // The ViewModel's initial state is Loading.
                assertEquals(PostsState.Loading, awaitItem())
                // After the use case fails, the state becomes Error.
                assertEquals(PostsState.Error(errorMessage), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
        launch {
            viewModel.effect.test {
                // A ShowError effect is also emitted on failure.
                assertEquals(PostsEffect.ShowError(errorMessage), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }*/

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}