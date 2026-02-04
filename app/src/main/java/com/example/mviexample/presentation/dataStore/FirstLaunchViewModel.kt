package com.example.mviexample.presentation.dataStore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mviexample.domain.usecase.datastore.GetFirstTimeUseCase
import com.example.mviexample.domain.usecase.datastore.GetPreferenceUseCase
import com.example.mviexample.domain.usecase.datastore.SaveFirstTimeUseCase
import com.example.mviexample.domain.usecase.datastore.SavePreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstLaunchViewModel @Inject constructor(
    private val getPref: GetPreferenceUseCase,
    private val savePref: SavePreferenceUseCase,
    private val getFirstTime: GetFirstTimeUseCase,
    private val saveFirstTime: SaveFirstTimeUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(FirstLaunchContract.State())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<FirstLaunchContract.Effect>()
    val effect = _effect.asSharedFlow()

    init {
        getUserFirstTime()
    }

    fun getUserFirstTime(){
        viewModelScope.launch {
            _state.update { it.copy(isFirstLaunch = getFirstTime().first()) }
        }
    }

    fun onEvent(event: FirstLaunchContract.Event) {
        when (event) {

            FirstLaunchContract.Event.OnAppStart -> {
                viewModelScope.launch {
                    if (getFirstTime().first()) {
                        _effect.emit(
                            FirstLaunchContract.Effect.NavigateToFirstLaunch
                        )
                    } else {
                        _effect.emit(
                            FirstLaunchContract.Effect.NavigateToHome
                        )
                    }
                }
            }

            FirstLaunchContract.Event.SaveFirstLaunch -> {
                viewModelScope.launch {
                    saveFirstTime(true)
                    _effect.emit(
                        FirstLaunchContract.Effect.NavigateToHome
                    )
                }
            }
        }
    }
}