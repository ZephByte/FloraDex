package org.floradex.app.feature.identifier

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import org.floradex.app.core.permissions.FloraDexPermission
import org.floradex.app.core.permissions.PermissionHandler
import org.floradex.app.core.permissions.PermissionHandlerState

@Composable
fun IdentifierView(
    onGoHomeClick: () -> Unit,
    onGoToSettingsClick: () -> Unit
) {
    PermissionHandler(
        permissions = listOf(FloraDexPermission.Camera, FloraDexPermission.Location),
        onGoToSettingsClick = onGoToSettingsClick
    ) { state ->
        if (state.allGranted) {
            CameraContent(
                onGoHomeClick = onGoHomeClick
            )
        } else {
            DeniedContent(
                state = state,
                onGoHomeClick = onGoHomeClick,
                onGoToSettingsClick = onGoToSettingsClick
            )
        }
    }
}

@Composable
private fun CameraContent(
    onGoHomeClick: () -> Unit
) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(modifier = Modifier.fillMaxSize())

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color.Black.copy(alpha = 0.4f))
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onGoHomeClick) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Go home",
                    tint = Color.White
                )
            }

            IconButton(
                onClick = {
                    Toast.makeText(context, "Photo captured", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.size(72.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.PhotoCamera,
                    contentDescription = "Capture photo",
                    tint = Color.Black,
                    modifier = Modifier.size(36.dp)
                )
            }

            // Invisible spacer to balance the row
            Spacer(modifier = Modifier.size(48.dp))
        }
    }
}

@Composable
private fun DeniedContent(
    state: PermissionHandlerState,
    onGoHomeClick: () -> Unit,
    onGoToSettingsClick: () -> Unit
) {
    LaunchedEffect(state.deniedPermissions) {
        if (!state.allGranted) {
            state.launchPermissionRequest()
        }
    }

    val hasSoftDenied = state.deniedPermissions.isNotEmpty()
    val hasPermanentlyDenied = state.permanentlyDeniedPermissions.isNotEmpty()

    val displayText = when {
        hasPermanentlyDenied && hasSoftDenied -> {
            val permNames = state.permanentlyDeniedPermissions
                .joinToString { it.title.removeSuffix(" Permission") }
            val softNames = state.deniedPermissions
                .joinToString { it.title.removeSuffix(" Permission") }
            "$permNames access has been permanently denied — please grant in Settings. " +
                "$softNames permission still needs to be granted."
        }
        hasPermanentlyDenied -> {
            val permNames = state.permanentlyDeniedPermissions
                .joinToString { it.title.removeSuffix(" Permission") }
            "$permNames access has been permanently denied. Please grant in Settings."
        }
        else -> {
            val permNames = state.deniedPermissions
                .joinToString { it.title.removeSuffix(" Permission") }
            "$permNames permission is required to identify plants."
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = displayText,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (hasSoftDenied) {
            TextButton(onClick = state.launchPermissionRequest) {
                Text("Grant Permissions")
            }
        }

        if (hasPermanentlyDenied) {
            TextButton(onClick = onGoToSettingsClick) {
                Text("Go to Settings")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        IconButton(onClick = onGoHomeClick) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Go home"
            )
        }
    }
}
