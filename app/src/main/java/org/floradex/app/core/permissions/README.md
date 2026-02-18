# Permissions Handling

## Overview

FloraDex uses a reusable `PermissionHandler` composable to manage Android runtime permissions. It wraps screen content in a permission-aware container that handles rationale dialogs, sequential multi-permission flows, and permanently-denied detection — so feature screens contain zero permission logic.

## Architecture

```
FloraDexPermission          — declares what (permission string, title, description, icon)
PermissionHandler           — manages how (Accompanist state, rationale flow, system dialogs)
PermissionRationaleDialogue — displays why (AlertDialog with rationale or settings prompt)
PermissionManager           — handles settings navigation (opens app settings via Intent)
```

## Adding Permissions to a Screen

### 1. Define the permission (if new)

In `core/permissions/FloraDexPermission.kt`, create a class implementing `FloraDexPermission`:

```kotlin
class BluetoothPermission : FloraDexPermission {
    override val title = "Bluetooth Permission"
    override val description = "This app needs Bluetooth to connect to sensors."
    override val permission = android.Manifest.permission.BLUETOOTH_CONNECT
    override val icon = Icons.Default.Bluetooth
}
```

### 2. Wrap your screen content

```kotlin
@Composable
fun MyFeatureView(onGoToSettingsClick: () -> Unit) {
    PermissionHandler(
        permissions = listOf(CameraPermission(), LocationPermission()),
        onGoToSettingsClick = onGoToSettingsClick
    ) { state ->
        // Your screen content here
        if (!state.allGranted) {
            Button(onClick = state.launchPermissionRequest) {
                Text("Grant permissions")
            }
        }
    }
}
```

### 3. That's it

`PermissionHandler` provides a `PermissionHandlerState` with:

| Field                     | Type         | Description                                    |
|---------------------------|--------------|------------------------------------------------|
| `allGranted`              | `Boolean`    | `true` when every listed permission is granted |
| `launchPermissionRequest` | `() -> Unit` | Starts the sequential permission flow          |

## How the Flow Works

1. User taps the button bound to `launchPermissionRequest`
2. A rationale dialog appears for the first ungranted permission
3. User taps "Grant permission" — the system permission dialog appears
4. After the user responds, the next ungranted permission's rationale auto-appears
5. This repeats until all permissions are granted or the user dismisses

### Edge cases handled automatically

- **Permanently denied**: If a permission was previously requested and `shouldShowRationale` is false, the dialog shows a "Go to settings" button instead of "Grant permission"
- **Dismiss at any point**: Closes the current dialog and stops the flow; the user can re-trigger with the button
- **Configuration changes**: All state uses `rememberSaveable`, so rotation mid-flow preserves progress

## Key Files

| File                                            | Purpose                                                            |
|-------------------------------------------------|--------------------------------------------------------------------|
| `core/permissions/FloraDexPermission.kt`        | Permission definitions (title, description, manifest string, icon) |
| `core/permissions/PermissionManager.kt`         | Opens app settings for permanently-denied permissions              |
| `core/permissions/PermissionHandler.kt`         | Reusable composable managing the full permission flow              |
| `core/permissions/PermissionRationaleDialog.kt` | The rationale AlertDialog UI                                       |
