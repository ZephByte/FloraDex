package org.floradex.app.feature.identifier

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.floradex.app.core.permissions.CameraPermission
import org.floradex.app.core.permissions.LocationPermission
import org.floradex.app.designsystem.PermissionHandler
import org.floradex.app.designsystem.theme.FloraDexTheme

@Composable
fun IdentifierView(
    onGoHomeClick: () -> Unit,
    onGoToSettingsClick: () -> Unit
) {
    PermissionHandler(
        permissions = listOf(CameraPermission(), LocationPermission()),
        onGoToSettingsClick = onGoToSettingsClick
    ) { state ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Identifier View")
            if (!state.allGranted) {
                Button(onClick = state.launchPermissionRequest) {
                    Text("Request permissions")
                }
            }
            Button(onClick = onGoHomeClick) {
                Text(text = "Go Home")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IdentifierViewPreview() {
    FloraDexTheme {
        IdentifierView(
            onGoHomeClick = { },
            onGoToSettingsClick = { }
        )
    }
}
