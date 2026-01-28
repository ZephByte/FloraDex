package org.floradex.app.designsystem

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.floradex.app.core.permissions.CameraPermission
import org.floradex.app.core.permissions.FloraDexPermission
import org.floradex.app.core.permissions.InternetPermission
import org.floradex.app.core.permissions.LocationPermission
import org.floradex.app.core.permissions.NotificationPermission

@Composable
fun PermissionRationaleDialogue(
    permission: FloraDexPermission,
    isPermanentlyDenied: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onGoToSettingsClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(imageVector = permission.icon, contentDescription = permission.title)
        },
        title = {
            Text(text = permission.title)
        },
        text = {
            val text = if (isPermanentlyDenied) {
                "It seems you have permanently denied the permission. You can grant it from the app settings."
            } else {
                permission.description
            }
            Text(
                text = text,
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            if (isPermanentlyDenied) {
                Button(onClick = onGoToSettingsClick) {
                    Text(text = "Go to settings")
                }
            } else {
                Button(onClick = onConfirm) {
                    Text(text = "Grant permission")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Dismiss")
            }
        }
    )
}

@Preview
@Composable
fun CameraPermissionRationalePreview() {
    val permission = CameraPermission()

    PermissionRationaleDialogue(
        permission = permission,
        isPermanentlyDenied = false,
        onDismiss = {},
        onConfirm = {},
        onGoToSettingsClick = {}
    )
}

@Preview
@Composable
fun CameraPermissionRationalePreviewPermanentlyDenied() {
    val permission = CameraPermission()

    PermissionRationaleDialogue(
        permission = permission,
        isPermanentlyDenied = true,
        onDismiss = {},
        onConfirm = {},
        onGoToSettingsClick = {}
    )
}

@Preview
@Composable
fun LocationPermissionRationalePreview() {
    val permission = LocationPermission()

    PermissionRationaleDialogue(
        permission = permission,
        isPermanentlyDenied = false,
        onDismiss = {},
        onConfirm = {},
        onGoToSettingsClick = {}
    )
}

@Preview
@Composable
fun InternetPermissionRationalePreview() {
    val permission = InternetPermission()

    PermissionRationaleDialogue(
        permission = permission,
        isPermanentlyDenied = false,
        onDismiss = {},
        onConfirm = {},
        onGoToSettingsClick = {}
    )
}

@Preview
@Composable
fun NotificationPermissionRationalePreview() {
    val permission = NotificationPermission()

    PermissionRationaleDialogue(
        permission = permission,
        isPermanentlyDenied = false,
        onDismiss = {},
        onConfirm = {},
        onGoToSettingsClick = {}
    )
}