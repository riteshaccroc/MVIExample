package com.example.mviexample.presentation.dataStore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FirstScreen(
    viewModel: FirstLaunchViewModel = hiltViewModel(),
    onNavigateHome: () -> Unit
) {
    val state = viewModel.state.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            if (it is FirstLaunchContract.Effect.NavigateToHome) {
                onNavigateHome()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hi User")
        Spacer(modifier = Modifier.padding(24.dp))
        Text("Welcome ${if(state.value.isFirstLaunch.not())"First" else "Second"} Time!!!")
        Button(onClick = {
            viewModel.onEvent(
                FirstLaunchContract.Event.SaveFirstLaunch
            )
        }) {
            Text("Continue")
        }
    }
}

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Home Screen")
    }
}