package org.floradex.app.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.floradex.app.feature.home.HomeView
import org.floradex.app.feature.identifier.IdentifierView

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomeScreen // Or Identifier if you prefer
    ) {
        composable<HomeScreen> {
            HomeView(navController = navController)
        }
        composable<IdentifierScreen> {
            IdentifierView(navController = navController)
        }
    }
}
