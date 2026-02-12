package org.floradex.app.feature.identifier

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.floradex.app.core.permissions.IPermissionManager
import javax.inject.Inject

@HiltViewModel
class IdentifierViewModel @Inject constructor(
    private val permissionManager: IPermissionManager
) : ViewModel() {

    fun onGoToSettingsClick()
    {
        permissionManager.launchPermissionSettings()
    }

}