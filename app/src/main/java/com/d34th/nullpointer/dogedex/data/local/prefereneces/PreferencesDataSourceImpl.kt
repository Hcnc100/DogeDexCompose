package com.d34th.nullpointer.dogedex.data.local.prefereneces

import com.d34th.nullpointer.dogedex.models.User
import kotlinx.coroutines.flow.Flow

class PreferencesDataSourceImpl(
    private val prefsUser: PreferencesUser
) : PreferencesDataSource {
    override val currentUser: Flow<User> = prefsUser.getUser()
    override val isFirstLoadingUser: Flow<Boolean> = prefsUser.getIsFirstLogin()
    override val isFirstRequestCameraPermission: Flow<Boolean> = prefsUser.getIsFirstCameraRequest()

    override suspend fun updateCurrentUser(user: User) {
        prefsUser.changeUser(user)
    }

    override suspend fun changeFirstRequestCameraPermission() {
        prefsUser.changeIsFirstCameraRequest(false)
    }

    override suspend fun changeIsFirstLoginUser() {
        prefsUser.changeIsFirstLogin(false)
    }

    override suspend fun clearDataSaved() {
        prefsUser.clearPreferences()
    }
}