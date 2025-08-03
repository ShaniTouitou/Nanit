package com.example.nanit

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nanit.navigation.AppNavigation
import com.example.nanit.network.WebSocketClient
import com.example.nanit.network.WebSocketDataSource
import com.example.nanit.repository.BirthdayRepo
import com.example.nanit.ui.theme.NanitTheme
import com.example.nanit.viewmodel.BirthdayViewModel

class MainActivity : ComponentActivity() {

    // Sets up the BirthdayViewModel using ViewModelProvider.Factory.
    private val birthdayViewModel: BirthdayViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val webSocketClient = WebSocketClient()
                val dataSource = WebSocketDataSource(webSocketClient)
                val repo = BirthdayRepo(dataSource)

                if (modelClass.isAssignableFrom(BirthdayViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return BirthdayViewModel(repo) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NanitTheme {
                AppNavigation(viewModel = birthdayViewModel)
            }
        }
    }
}
