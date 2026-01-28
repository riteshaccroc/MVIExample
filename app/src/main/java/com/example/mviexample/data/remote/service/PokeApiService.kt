package com.example.mviexample.data.remote.service

import com.example.mviexample.data.remote.dto.poke.PokeDto
import com.example.mviexample.data.remote.dto.post.PostDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemon(
        @Query("offset") page: Int,
        @Query("limit") pageCount: Int
    ): PokeDto

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }
    //https://pokeapi.co/api/v2/pokemon?limit=10&offset=20
}