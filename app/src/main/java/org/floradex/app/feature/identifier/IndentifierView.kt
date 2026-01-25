package org.floradex.app.feature.identifier

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.floradex.app.core.navigation.HomeScreen

@Composable
fun IdentifierView(
    navController: NavController,
    onGoToSettingsClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Identifier View")
        Button(onClick = { navController.navigate(HomeScreen) }) {
            Text(text = "Go Home")
        }

        Button(onClick = { onGoToSettingsClick() }) {
            Text(text = "Go To Settings")
        }
    }
}
