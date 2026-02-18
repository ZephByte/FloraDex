package org.floradex.app.core.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeoutOrNull

data class PermissionHandlerState(
    val allGranted: Boolean,
    val anyPermanentlyDenied: Boolean,
    val permanentlyDeniedPermissions: List<FloraDexPermission>,
    val deniedPermissions: List<FloraDexPermission>,
    val launchPermissionRequest: () -> Unit
)

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionHandler(
    permissions: List<FloraDexPermission>,
    onGoToSettingsClick: () -> Unit,
    content: @Composable (PermissionHandlerState) -> Unit
) {
    val multiplePermissionsState = rememberMultiplePermissionsState(
        permissions.map { it.permission }
    )

    var currentRationaleIndex by rememberSaveable { mutableIntStateOf(-1) }
    var requestedPermissions by rememberSaveable { mutableStateOf(listOf<String>()) }
    var waitingForResult by rememberSaveable { mutableStateOf(false) }

    // When waiting for a system dialog result, detect status change and advance
    LaunchedEffect(waitingForResult) {
        if (!waitingForResult) return@LaunchedEffect

        withTimeoutOrNull(5_000) {
            snapshotFlow {
                multiplePermissionsState.permissions.map { it.status }
            }.drop(1).first()
        }

        waitingForResult = false

        // Find next ungranted permission and show its rationale
        val nextIndex = permissions.indexOfFirst { floraPerm ->
            multiplePermissionsState.permissions
                .find { it.permission == floraPerm.permission }
                ?.status?.isGranted == false
        }
        currentRationaleIndex = nextIndex
    }

    // Show rationale dialog for current permission
    if (currentRationaleIndex in permissions.indices) {
        val currentPermission = permissions[currentRationaleIndex]
        val permissionState = multiplePermissionsState.permissions.find {
            it.permission == currentPermission.permission
        }

        if (permissionState != null && !permissionState.status.isGranted) {
            val isPermanentlyDenied = !permissionState.status.shouldShowRationale &&
                currentPermission.permission in requestedPermissions

            PermissionRationaleDialogue(
                permission = currentPermission,
                isPermanentlyDenied = isPermanentlyDenied,
                onDismiss = { currentRationaleIndex = -1 },
                onConfirm = {
                    requestedPermissions = requestedPermissions + currentPermission.permission
                    currentRationaleIndex = -1
                    waitingForResult = true
                    permissionState.launchPermissionRequest()
                },
                onGoToSettingsClick = {
                    currentRationaleIndex = -1
                    onGoToSettingsClick()
                }
            )
        }
    }

    val permanentlyDeniedPermissions = permissions.filter { floraPerm ->
        val perm = multiplePermissionsState.permissions
            .find { it.permission == floraPerm.permission }
        perm != null &&
            !perm.status.isGranted &&
            !perm.status.shouldShowRationale &&
            perm.permission in requestedPermissions
    }

    val deniedPermissions = permissions.filter { floraPerm ->
        val perm = multiplePermissionsState.permissions
            .find { it.permission == floraPerm.permission }
        perm != null &&
            !perm.status.isGranted &&
            floraPerm !in permanentlyDeniedPermissions
    }

    content(
        PermissionHandlerState(
            allGranted = multiplePermissionsState.allPermissionsGranted,
            anyPermanentlyDenied = permanentlyDeniedPermissions.isNotEmpty(),
            permanentlyDeniedPermissions = permanentlyDeniedPermissions,
            deniedPermissions = deniedPermissions,
            launchPermissionRequest = {
                val firstUngrantedIndex = permissions.indexOfFirst { floraPerm ->
                    multiplePermissionsState.permissions
                        .find { it.permission == floraPerm.permission }
                        ?.status?.isGranted == false
                }
                currentRationaleIndex = firstUngrantedIndex
            }
        )
    )
}
