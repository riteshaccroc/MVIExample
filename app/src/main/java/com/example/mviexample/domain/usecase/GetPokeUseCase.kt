package com.example.mviexample.domain.usecase

import androidx.paging.PagingData
import com.example.mviexample.data.ApiStatus
import com.example.mviexample.domain.model.Poke
import com.example.mviexample.domain.model.Post
import com.example.mviexample.domain.repository.PokeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPokeUseCase @Inject constructor(
    private val pokeRepository: PokeRepository
) {
    operator fun invoke(page:Int): Flow<PagingData<Post>> = flow {
//        emit(ApiStatus.Loading())
//
//        val result = pokeRepository.getPokemon(page, pageCount = 10)
//
//        result.fold(
//            onSuccess = { posts -> emit(ApiStatus.Success(posts)) },
//            onFailure = { error -> emit(ApiStatus.Error(error.message ?: "Unknown error")) }
//        )
    }
}