package com.example.mviexample.data.repository

import com.example.mviexample.data.remote.dto.poke.resultDto
import com.example.mviexample.data.remote.service.PokeApiService
import com.example.mviexample.domain.model.Poke
import com.example.mviexample.domain.repository.PokeRepository
import javax.inject.Inject

class PokeRepositoryImpl @Inject constructor(
    private val apiService: PokeApiService
) : PokeRepository {

    override suspend fun getPokemon(page: Int, pageCount: Int): Result<Poke> {
        return try {
            val response = apiService.getPokemon(page, pageCount)
            val pokes =
                Poke(
                    count = response.count,
                    next = response.next,
                    previous = response.previous,
                    results = response.results.map { resultDto -> resultDto(resultDto) }
                )

            Result.success(pokes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
