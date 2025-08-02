package com.example.nanit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import com.example.nanit.network.WebSocketClient
import com.example.nanit.screens.ConnectScreen
import com.example.nanit.ui.theme.NanitTheme

class MainActivity : ComponentActivity() {

    // region OnCreate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NanitTheme {
                val webSocketClient = remember { WebSocketClient() }

                // Connect to the server
                ConnectScreen { ip, port ->
                    webSocketClient.connect(ip, port)
                }
            }
        }
    }

    // endregion

}

