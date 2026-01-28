package com.example.mviexample.presentation.pokemon

import com.example.mviexample.domain.model.Poke

sealed class PokeEvent {
    object LoadPosts : PokeEvent()
    object Retry : PokeEvent()
    data class PostClicked(val poke: Poke) : PokeEvent()
}

sealed class PokeEffect {
    data class ShowError(val message: String) : PokeEffect()
    data class NavigateToPostDetail(val poke: Poke) : PokeEffect()
}

data class PokeState(
    var isLoading: Boolean = false,
    var data: Poke? = null,
    var error: String = ""
)