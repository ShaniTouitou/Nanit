package com.example.nanit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.example.nanit.ui.theme.NanitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NanitTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    ConnectScreen { ip, port ->
        Log.d("ConnectScreen", "Connecting to $ip:$port")
    }
}