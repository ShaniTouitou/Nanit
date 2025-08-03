package com.example.nanit.navigation

/**
 * Represent the screens for the navigation.
 */
sealed class NavRoutes(val route: String) {
    object Connect : NavRoutes("connect")
    object Birthday : NavRoutes("birthday")
}
