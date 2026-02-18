package org.floradex.app.core.permissions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.ui.graphics.vector.ImageVector

sealed interface FloraDexPermission {
    val title: String
    val description: String
    val permission: String
    val icon: ImageVector

    data object Camera : FloraDexPermission {
        override val title = "Camera Permission"
        override val description =
            "This app needs access to your camera to take pictures of plants."
        override val permission = android.Manifest.permission.CAMERA
        override val icon = Icons.Default.Camera
    }

    data object Location : FloraDexPermission {
        override val title = "Location Permission"
        override val description =
            "This app needs access to your location to tag where plants were discovered."
        override val permission = android.Manifest.permission.ACCESS_FINE_LOCATION
        override val icon = Icons.Default.LocationOn
    }

    data object Notification : FloraDexPermission {
        override val title = "Notification Permission"
        override val description =
            "This app needs to send you notifications to..." // TODO Zeph: fill this in with correct description
        override val permission = android.Manifest.permission.POST_NOTIFICATIONS
        override val icon = Icons.Default.Notifications
    }
}
