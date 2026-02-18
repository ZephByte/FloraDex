package org.floradex.app.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.floradex.app.feature.home.HomeView
import org.floradex.app.feature.home.HomeViewModel
import org.floradex.app.feature.identifier.IdentifierView
import org.floradex.app.feature.identifier.IdentifierViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = HomeScreen // Or Identifier if you prefer
    ) {
        composable<HomeScreen> {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeView(
                onGoToIdentifierClick = {
                    navController.navigate(IdentifierScreen)
                }
            )
        }
        composable<IdentifierScreen> {
            val viewModel: IdentifierViewModel = hiltViewModel()
            IdentifierView(
                onGoHomeClick = {
                    navController.navigate(HomeScreen)
                },
            )
        }
    }
}
