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

    // region Members

    /**
     * Sets up the BirthdayViewModel using ViewModelProvider.Factory.
     */
    private val birthdayViewModel: BirthdayViewModel by viewModels {
        // Create an instance of the view model.
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                // Connection to the server.
                val webSocketClient = WebSocketClient()
                val dataSource = WebSocketDataSource(webSocketClient)

                val repo = BirthdayRepo(dataSource)

                if (modelClass.isAssignableFrom(BirthdayViewModel::class.java)) {
                    // Already check that is view model.
                    @Suppress("UNCHECKED_CAST")

                    // Return the view model instance.
                    return BirthdayViewModel(repo) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    // endregion

    // region onCreate

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NanitTheme {
                AppNavigation(viewModel = birthdayViewModel)
            }
        }
    }

    // endregion

}
