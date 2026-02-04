package com.example.mviexample.presentation.dataStore
object FirstLaunchContract {

    sealed interface Event {
        object OnAppStart : Event
        object SaveFirstLaunch : Event
    }

    data class State(
        val isFirstLaunch: Boolean = false
    )

    sealed interface Effect {
        object NavigateToHome : Effect
        object NavigateToFirstLaunch : Effect
    }
}