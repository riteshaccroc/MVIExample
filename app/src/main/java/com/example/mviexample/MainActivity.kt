package com.example.mviexample

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.mviexample.data.dataStore.proto.AppParamsSerializer
import com.example.mviexample.proto.AppParams
import com.example.mviexample.proto.appParams
import com.example.mviexample.ui.theme.MVIExampleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.IOException

val Context.appParamPref: DataStore<AppParams> by dataStore(
    fileName = "app_params.pb",
    serializer = AppParamsSerializer
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MVIExampleTheme {
                /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }*/
                /*val navController = rememberNavController()
                AppNavigation(navController = navController)*/
                MainScreen()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MVIExampleTheme {
        Greeting("Android")
    }
}

@Composable
fun MainScreen() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current

        val userSettingsFlow: Flow<AppParams> =
            context.appParamPref.data.map { preferences ->
                preferences
            }

        val appParams = runBlocking { userSettingsFlow.first() }

        Text(text = "Old values")
        Spacer(modifier = Modifier.padding(16.dp))
        Text(text = "appParams startupCounter: ${appParams.startupCounter}")
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = "appParams startupTimestamp: ${appParams.startupTimestamp}")
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = "appParams showCompleted: ${appParams.showCompleted}")
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = "appParams cookie: ${appParams.cookie}")
        Spacer(modifier = Modifier.padding(4.dp))
        Spacer(modifier = Modifier.padding(16.dp))
        Button(onClick = { runBlocking { saveAppParamsCompleteObj1(context) } }) { Text("Data 1") }
        Spacer(modifier = Modifier.padding(16.dp))
        Button(onClick = { runBlocking { saveAppParamsCompleteObj2(context) } }) { Text("Data 2") }
        Spacer(modifier = Modifier.padding(16.dp))
        Button(onClick = { runBlocking { saveAppParamsOneObj(context) } }) { Text("Single Data update") }
        Spacer(modifier = Modifier.padding(16.dp))
    }
}

suspend fun saveAppParamsCompleteObj1(context: Context) {

    // update complete object
    context.appParamPref.updateData {
        appParams {
            showCompleted = false
            startupCounter = 1
            startupTimestamp = System.currentTimeMillis()
            cookie = "cookie data : 11111111111111111111111111111111"
        }
    }
}

suspend fun saveAppParamsCompleteObj2(context: Context) {
    context.appParamPref.updateData {
        // We ignore the current 'it' and return a brand new built object
        AppParams.newBuilder()
            .setShowCompleted(true)
            .setStartupCounter(5)
            .setStartupTimestamp(System.currentTimeMillis())
            .setCookie("cookie data :99999999999999999999999")
            .build()
    }
}

suspend fun saveAppParamsOneObj(context: Context) {
    //update one property at a time
    context.appParamPref.updateData { preferences ->
        preferences.toBuilder()
//            .clear()
            .setStartupCounter(123)
//            .setShowCompleted(true)
            .setStartupTimestamp(123456)
            .build() // Build the updated object
    }
}

/*
const val PREFERENCES_NAME = "my_preferences"
val EXAMPLE_COUNTER = intPreferencesKey("example_counter")
private val USER_PREFERENCES_NAME = "user_preferences"

class MyPreferences(private val context: Context) {

    // This creates an instance as an extension property of Context
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

    fun counterFlow(): Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[EXAMPLE_COUNTER] ?: 0
    }

    suspend fun incrementCounter() {
        context.dataStore.updateData {
            it.toMutablePreferences().also { preferences ->
                preferences[EXAMPLE_COUNTER] = (preferences[EXAMPLE_COUNTER] ?: 0) + 1
            }
        }
    }
}*/
