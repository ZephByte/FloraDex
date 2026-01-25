package org.floradex.app.core.permissions.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.floradex.app.core.permissions.IPermissionManager
import org.floradex.app.core.permissions.PermissionManager

@Module
@InstallIn(SingletonComponent::class)
abstract class PermissionsModule {

    @Binds
    abstract fun bindPermissions(
        permissionManager: PermissionManager
    ): IPermissionManager

}