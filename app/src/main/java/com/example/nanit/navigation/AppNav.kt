package com.example.nanit.navigation

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nanit.mvi.BirthdayEvent
import com.example.nanit.screens.BirthdayScreen
import com.example.nanit.screens.ConnectScreen
import com.example.nanit.viewmodel.BirthdayViewModel

/**
 * Represent the navigation between the screens.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(viewModel: BirthdayViewModel) {

    // region Members

    val navController = rememberNavController()

    val state = viewModel.state.collectAsState()

    // endregion

    // region LaunchedEffect

    /**
     * When getting data navigate to the birthday screen.
     */
    LaunchedEffect(state.value.birthdayData) {
        if (state.value.birthdayData != null) {
            navController.navigate(NavRoutes.Birthday.route) {
                popUpTo(NavRoutes.Connect.route) { inclusive = true }
            }
        }
    }

    // endregion

    // region NavHost

    NavHost(navController = navController, startDestination = NavRoutes.Connect.route) {
        composable(NavRoutes.Connect.route) {
            ConnectScreen(onConnect = { ip, port ->
                viewModel.onEvent(BirthdayEvent.ConnectClicked(ip, port))
            })
        }

        composable(NavRoutes.Birthday.route) {
            BirthdayScreen(state = state.value, onConnect = {}, viewModel)

            BackHandler {
                // Try to go back to the previous screen.
                val previousScreen = navController.popBackStack()

                // If there is no previous screen found, navigate to Connect screen.
                if (!previousScreen) {
                    navController.navigate(NavRoutes.Connect.route) {
                        // Ensures the previous screen is cleared from the back stack.
                        popUpTo(NavRoutes.Birthday.route) { inclusive = true }
                    }
                }
            }
        }
    }

    // endregion

}
