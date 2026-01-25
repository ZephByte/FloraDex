package org.floradex.app.core.permissions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface IPermissionManager {
    fun launchPermissionSettings()
}

@Singleton
class PermissionManager @Inject constructor(
    @ApplicationContext private val context: Context
): IPermissionManager {

    override fun launchPermissionSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = Uri.fromParts("package", context.packageName, null)
        context.startActivity(intent)
    }
}
