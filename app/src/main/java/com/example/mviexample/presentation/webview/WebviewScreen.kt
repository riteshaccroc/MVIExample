package com.example.mviexample.presentation.webview

import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

const val LOGIN_URL: String =
    "https://login.eu.cos.com/login.eu.cos.com/b2c_1a_cos_signup_signin/oauth2/v2.0/authorize?client_id=fbdf2ef7-4f41-41c9-bab4-9e6895d63407&scope=https%3A%2F%2Flogin.il.hm.com%2Fcustomer_api_usertoken%2Fsensitive%20https%3A%2F%2Flogin.il.hm.com%2Fcustomer_api_usertoken%2Fpii%20https%3A%2F%2Flogin.il.hm.com%2Fcustomer_api_usertoken%2Fpci%20openid%20profile%20offline_access&redirect_uri=https%3A%2F%2Fwww.cos.com%2Fauth-callback%2Feu&client-request-id=019bff8f-ceae-75f9-9f4f-7dd2261e0f2e&response_mode=fragment&response_type=code&x-client-SKU=msal.js.browser&x-client-VER=3.30.0&client_info=1&code_challenge=v29e2aNYERlMAzug6OawtMBrtNngRq600w1SPRk5Uv0&code_challenge_method=S256&nonce=019bff8f-cec3-7543-ab44-68d1887a5b19&state=eyJpZCI6IjAxOWJmZjhmLWNlYWUtNzRjOS1hNTljLWZiYjg3OGIwZWEzOSIsIm1ldGEiOnsiaW50ZXJhY3Rpb25UeXBlIjoicmVkaXJlY3QifX0%3D%7C%2Fen-gb%2Faccount%2Fmy-account%2Fpersonal-info%7Chttps%3A%2F%2Fwww.cos.com%2Fen-gb%2Fmen&brand=COS&countryCode=GB&locale=en_GB&ui_locales=en&site_locale=gb"

@Composable
fun WebViewSafeScreen(onButtonClick: () -> Unit) {
    // State management for back press (best practice)
    var webView: WebView? by remember { mutableStateOf(null) }
    var canGoBack by remember { mutableStateOf(false) }

    BackHandler(enabled = canGoBack) {
        webView?.goBack()
    }

    // Main container with status bar padding
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding() // Pushes content below the notification bar
            .navigationBarsPadding(), // Prevents overlap with bottom navigation bar
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //TODO: try remove this warning
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    settings.javaScriptEnabled = true
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            canGoBack = view?.canGoBack() ?: false
                        }
                    }
                    webView = this
                    loadUrl(LOGIN_URL)
                }
            },
            update = { /* Updates handled by state */ }
        )

        //TODO:MVI pattern ??
        Button(onClick = { onButtonClick() }, modifier = Modifier.padding(16.dp)) {
            Text(text = "Go to Cart")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebviewScreen() {
    var webView: WebView? by remember { mutableStateOf(null) }
    var canGoBack by remember { mutableStateOf(false) }

    // 2. Back press logic: Only active if web history exists
    BackHandler(enabled = canGoBack) {
        webView?.goBack()
    }
    Scaffold(
        // 1. Define the Top App Bar
        topBar = {
            TopAppBar(
                title = { Text("Login") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { innerPadding ->
        // 2. Apply Scaffold's padding to the WebView container
        Box(
            modifier = Modifier
                .padding(innerPadding) // Ensures it stays below the app bar/notch
                .fillMaxSize()
        ) {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )

                        // Configure basic settings
                        settings.javaScriptEnabled = true

                        // 3. JavaScript Interface
                        addJavascriptInterface(WebAppInterface { user, token ->
                            // Handle login data here
                        }, "AndroidBridge")

                        // 4. Update back state whenever a page is loaded
                        webViewClient = object : WebViewClient() {
                            override fun onPageFinished(view: WebView?, url: String?) {
                                canGoBack = view?.canGoBack() ?: false
                            }
                        }

                        webView = this
                        loadUrl(LOGIN_URL)
                    }
                },
                update = {
                    // Warning-free: Do not call Composables here.
                    // Only view-based updates like it.loadUrl(url)
                }
            )
        }
    }
}

// Javascript Interface Class
class WebAppInterface(private val onLoginSuccess: (String, String) -> Unit) {
    @JavascriptInterface
    fun sendLoginData(username: String, token: String) {
        onLoginSuccess(username, token)
    }
}