package com.example.mviexample.domain.repository

import com.example.mviexample.domain.model.Poke

interface PokeRepository {
    suspend fun getPokemon(page: Int, pageCount: Int): Result<Poke>
}