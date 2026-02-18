package org.floradex.app.core.permissions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.ui.graphics.vector.ImageVector

interface FloraDexPermission {
    val title: String
    val description: String
    val permission: String
    val icon: ImageVector
}

class CameraPermission : FloraDexPermission {
    override val title: String
        get() = "Camera Permission"
    override val description: String
        get() = "This app needs access to your camera to take pictures of plants."
    override val permission: String
        get() = android.Manifest.permission.CAMERA
    override val icon: ImageVector
        get() = Icons.Default.Camera
}

class LocationPermission : FloraDexPermission {
    override val title: String
        get() = "Location Permission"
    override val description: String
        get() = "This app needs access to your location to tag where photos were taken."
    override val permission: String
        get() = android.Manifest.permission.ACCESS_FINE_LOCATION
    override val icon: ImageVector
        get() = Icons.Default.LocationOn
}

class InternetPermission : FloraDexPermission {
    override val title: String
        get() = "Internet Permission"
    override val description: String
        get() = "This app needs access to the internet to identify plants."
    override val permission: String
        get() = android.Manifest.permission.INTERNET
    override val icon: ImageVector
        get() = Icons.Default.Wifi
}

class NotificationPermission : FloraDexPermission {
    override val title: String
        get() = "Notification Permission"
    override val description: String
        get() = "This app needs to send you notifications to remind you to water your plants."
    override val permission: String
        get() = android.Manifest.permission.POST_NOTIFICATIONS
    override val icon: ImageVector
        get() = Icons.Default.Notifications
}
