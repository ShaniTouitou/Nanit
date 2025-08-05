package com.example.nanit.navigation

/**
 * Represent the screens for the navigation.
 */
sealed class NavRoutes(val route: String) {
    // Connect server screen.
    object Connect : NavRoutes("connect")

    // Birthday screen.
    object Birthday : NavRoutes("birthday")
}
