package org.floradex.app.feature.identifier

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import org.floradex.app.core.permissions.CameraPermission
import org.floradex.app.core.permissions.PermissionManager
import org.floradex.app.designsystem.PermissionRationaleDialogue
import org.floradex.app.designsystem.theme.FloraDexTheme

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun IdentifierView(
    onGoHomeClick: () -> Unit,
) {

    var showPermissionDialogue by rememberSaveable { mutableStateOf(true) }
    var permissionRequested by rememberSaveable { mutableStateOf(false) }
    val uiPermission = CameraPermission()
    val permissionState = rememberPermissionState(uiPermission.permission)

    val permissionManager = PermissionManager(
        context = androidx.compose.ui.platform.LocalContext.current
    )

    if (!permissionState.status.isGranted && showPermissionDialogue) {
        PermissionRationaleDialogue(
            permission = uiPermission,
            isPermanentlyDenied = !permissionState.status.shouldShowRationale && permissionRequested,
            onDismiss = { showPermissionDialogue = false },
            onConfirm = {
                permissionState.launchPermissionRequest()
                permissionRequested = true
                showPermissionDialogue = false
            },
            onGoToSettingsClick = {
                permissionManager.launchPermissionSettings()
                showPermissionDialogue = false
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Welcome to the Identifier View")
        if (!permissionState.status.isGranted) {
            Button(onClick = { showPermissionDialogue = true }) {
                Text("Request permission")
            }
        }
        Button(onClick = { onGoHomeClick() }) {
            Text(text = "Go Home")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IdentifierViewPreview() {
    FloraDexTheme {
        IdentifierView(
            onGoHomeClick = { }
        )
    }
}
