package com.savannmaker.listify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.savannmaker.listify.ui.theme.ListifyTheme

import android.content.Intent
import android.util.Log
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse


class MainActivity : ComponentActivity() {
    private val clientId = "c13e6177304e4fcabd51dfeafb6f6844"
    private val redirectUri = "listify://callback"
    private val requestCode = 1337

    private fun loginToSpotify() {
        val builder = AuthorizationRequest.Builder(clientId, AuthorizationResponse.Type.CODE, redirectUri)
        builder.setScopes(arrayOf("playlist-read-private", "playlist-read-collaborative"))
        val request = builder.build()
        AuthorizationClient.openLoginActivity(this, requestCode, request)
    }
    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (this.requestCode == requestCode) {
            val response = AuthorizationClient.getResponse(resultCode, intent)

            when (response.type) {
                AuthorizationResponse.Type.CODE -> { Log.d("SpotifyLogin", "Code:${response.code}") }
                AuthorizationResponse.Type.ERROR -> { Log.d("SpotifyLogin", "Error:${response.error}") }
                else -> { Log.d("SpotifyLogin", "Response hit else clause somehow. Idk") }

            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListifyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        loginToSpotify()
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
    ListifyTheme {
        Greeting("Android")
    }
}