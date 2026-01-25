package org.floradex.app.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.floradex.app.feature.home.HomeView
import org.floradex.app.feature.identifier.IdentifierView
import org.floradex.app.feature.identifier.IdentifierViewModel

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
            val viewModel: IdentifierViewModel = hiltViewModel()
            IdentifierView(
                navController = navController,
                onGoToSettingsClick = viewModel::onGoToSettingsClick
            )
        }
    }
}
