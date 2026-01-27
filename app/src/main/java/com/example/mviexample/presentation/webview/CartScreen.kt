package com.example.mviexample.presentation.webview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CartScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Cart Screen",modifier = Modifier.padding(16.dp))

        Button(onClick = { }, modifier = Modifier.padding(16.dp)) {
            Text(text = "Button 1")
        }

        Button(onClick = { }, modifier = Modifier.padding(16.dp)) {
            Text(text = "Button 2")
        }
    }
}