package com.example.mviexample.data.remote.dto.poke

import com.google.gson.annotations.SerializedName


data class PokeDto(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String,
    @SerializedName("previous") val previous: Any,
    @SerializedName("results") val results: List<ResultDto>
)

data class ResultDto(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)

fun resultDto(resultDto: ResultDto) =
    com.example.mviexample.domain.model.Result(
        name = resultDto.name,
        url = resultDto.url
    )


